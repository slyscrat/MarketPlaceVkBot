package com.bot.vk.vkbot.core;

import com.bot.vk.vkbot.core.client.VkClient;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.bot.vk.vkbot.exceptions.RudeWordException;
import com.bot.vk.vkbot.service.BanService;
import com.bot.vk.vkbot.validators.*;
import com.bot.vk.vkbot.service.RudeWordsFilter;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Dialog;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.bot.vk.vkbot.entity.Item;
import com.bot.vk.vkbot.service.ItemService;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j2
public class VkClientImpl implements VkClient {

    private final VkApiClient apiClient = new VkApiClient(HttpTransportClient.getInstance());
    private final Validator addValidator;
    private final Validator editValidator;
    private final Validator findValidator;
    private final Validator deleteValidator;
    private GroupActor groupActor;
    private UserActor userActor;
    private final ItemService itemService;
    private Properties marketProps = new Properties();
	private final BanService banService;
    private final RudeWordsFilter rudeWordsFilter;

    @Value("${bot.group.id}")
    private int groupID;

    @Value("${bot.user.id}")
    private int userID;

    @Value("${bot.group.client_secret}")
    private String groupToken;

    @Value("${bot.user.client_secret}")
    private String userToken;

    @Autowired
    public VkClientImpl(BanService banService, RudeWordsFilter rudeWordsFilter, ItemService itemService, AddValidator addValidator, EditValidator editValidator, DeleteValidator deleteValidator, FindValidator findValidator) {
        this.banService = banService;
        this.rudeWordsFilter = rudeWordsFilter;
        this.itemService = itemService;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
        this.deleteValidator = deleteValidator;
        this.findValidator = findValidator;
    }

    @PostConstruct
    public void init() throws IOException {
        this.groupActor = new GroupActor(groupID, groupToken);
        this.userActor = new UserActor(userID, userToken);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("chat.properties");
        Reader reader = new InputStreamReader(stream, "UTF-8");
        marketProps.load(reader);
        stream.close();
    }

    //remove after usage
    private void sendBatchMessages(String message, int userId) {
        Random random = new Random();
        try {
            List<AbstractQueryBuilder> list = new ArrayList<>();
            for (int i = 0; i < 25; i++)
            {
                list.add(apiClient.messages().send(groupActor).message("message " + (i+1)).attachment("photo-" + groupID + "_" + 456239026).userId(userId).randomId(random.nextInt()));
            }
            apiClient.execute().batch(groupActor, list).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    //rewrite
    @Override
    public void sendMessage(String message, int userId) {
        Random random = new Random();
        try {
            this.apiClient.messages().send(groupActor).message(message).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    public void sendMessage(String message, List<String> attachment, int userId) {
        Random random = new Random();
        try {
            this.apiClient.messages().send(groupActor).message(message).attachment(attachment).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    @Override
    public List<Message> readMessages() {
        List<Message> result = new ArrayList<Message>();
        try {
            List<Dialog> dialogs = apiClient.messages().getDialogs(groupActor).unanswered1(true).execute().getItems();
            for (Dialog item : dialogs) {
                result.add(item.getMessage());
            }
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return result;
    }

    //add + refactor
    @Override
    public void replyForMessages(List<Message> messages) {
        String body;
        List<MessageAttachment> list;
        for (Message message : messages) {
            body = message.getBody();

			//rude words
            try {
                rudeWordsFilter.assertSentenceIsPolite(body);
            } catch (RudeWordException e) {
                log.info("User {} is not a polite guy", message.getUserId());
                Integer warningsCount = banService.addWarning(message.getUserId().longValue());
                if (banService.isUserBanned(message.getUserId().longValue())) {
                    sendMessage("бан", message.getUserId());
                    try {
                        apiClient.groups().banUser(userActor, groupID, message.getUserId()).comment("Вы были забанены за использование мата").execute();
                    } catch (ApiException | ClientException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }
                sendMessage(marketProps.getProperty("chat.message.ban.warning") + (Integer.parseInt(marketProps.getProperty("chat.message.ban.maxcount")) - warningsCount), message.getUserId());
                continue;
            }

            list = message.getAttachments();
            if (body.equals("Начать")) {
                sendMessage(marketProps.getProperty("chat.message.welcome"), message.getUserId());
            } else if (body.contains("/info")) {
                sendMessage(getMarketInfo(body), message.getUserId());
            } else if (body.contains("/edit") && editValidator.isValid(body) && isExistAndOwner(message.getUserId(), 12L)) {
                //edit
                // CHANGE itemId in isExistAndOwner !!!!!!!!!!!!!!!!!!!!!!!!!!!!
            } else if (body.contains("/add") && addValidator.isValid(body)) {
                Pattern pattern = Pattern.compile("(/add)\\ \\\"([^\"]{4,100})\\\"\\ \\\"([^\"]{10,})\\\"\\ ([0-9]+)\\ ([0-9.]+)");
                Matcher matcher = pattern.matcher(body);
                matcher.find();

                int productId = postProduct(message.getUserId().longValue(),
                        matcher.group(2),
                        matcher.group(3),
                        Long.parseLong(matcher.group(4)),
                        Float.parseFloat(matcher.group(5)),
                        list.get(0).getPhoto());
                sendMessage(marketProps.getProperty("chat.message.add.successmessage.id") +
                        " " + productId + "\n"
                        + marketProps.getProperty("chat.message.add.successmessage.warning"), message.getUserId());
            } else if (body.contains("/find") && findValidator.isValid(body)) {
                //regex
                Pattern pattern = Pattern.compile("(\\/find)\\ \\\"([^\\\"]+)\\\"");
                Matcher matcher = pattern.matcher(body);
                matcher.find();
                log.info(matcher.group(2).toLowerCase());
                List<Item> items = itemService.getByString(matcher.group(2).toLowerCase());
                List<String> attachments = new ArrayList<>();
                String messageBody = "";
                if (items.size() != 0) {
                    //sendMessage("1) " + items.get(0).getName() + " (" + items.get(0).getPrice() + " рублей)\n", item.getUserId());
                    for (int i = 0; i < items.size(); i++) {
                        messageBody += (i + 1) + ") " + items.get(i).getName() + " (" + items.get(i).getPrice() + " рублей)\n";
                        attachments.add("photo-" + groupID + "_" + items.get(i).getPictureId());
                    }
                    sendMessage(messageBody, attachments, message.getUserId());
                } else {
                    sendMessage("Ничего не найдено", message.getUserId());
                }
            } else if (body.contains("/delete") && deleteValidator.isValid(body)) {
                String[] params = body.split(" ");
                Long itemId = Long.parseLong(params[1]);
                if (isExistAndOwner(message.getUserId(), itemId)) {
                    deleteProduct(itemId);
                    sendMessage(marketProps.getProperty("chat.message.delete.successmessage"), message.getUserId());
                }

            } else {
                sendMessage(marketProps.getProperty("chat.message.error"), message.getUserId());
            }
            /*  catch(Exception ex){
                log.error(ex);
                sendMessage("Возникла ошибка! Пожалуйста, убедитесь в правильности введенной команды", message.getUserId());
                sendMessage(marketProps.getProperty("market.info." + body.split(" ")[0].substring(1)), message.getUserId());
            }*/
        }
    }

    public boolean isExistAndOwner(Integer userId, Long itemId){
        try{
            if (this.itemService.getById(itemId).getUserId().longValue() == userId)
                return true;
            else
                throw new Exception("Вы не можете удалить данный товар, так как не являетесь его владельцем!");
        }
        catch(ObjectNotFoundException ex){
            log.error(ex);
            sendMessage("Товар с данным идентификатором не найден!", userId);
        }
        catch(Exception ex){
            log.error(ex);
            sendMessage(ex.getMessage(), userId);
        }
        return false;
    }

    //write
    @Override
    public List<Message> sendMessagesRestrictor(List<Message> messages) {
        return null;
    }

    //refactor
    @Override
    public int postProduct(Long userId, String name, String description, Long type, Float price, Photo photo) {
        try {
            int photoId = getMarketUploadedPhotoId(photo, true); //api user call +2
            int productID = apiClient.market().add(userActor, -1*groupID, name, description, type.intValue(), price, photoId).execute().getMarketItemId();
            this.itemService.create(new Item((long) productID, userId, name, description, photoId, price, type));
            return productID;
        } catch (IOException | ClientException | ApiException e) {
            log.error(e);
            return 0;
        }
    }

    //refactor
    @Override
    public void deleteProduct(Long id) {
        try {
            this.itemService.deleteById(id);
            apiClient.market().delete(userActor, -1*groupID, id.intValue()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    //write
    @Override
    public void banUser(int id) {
        //ban
    }

    //write
    @Override
    public void unBanUser(int id) {
        //unban
    }

    @Override
    public String getMarketInfo(String command) {
        String property;
        if (command.contains("add"))
        {
            property = "market.info.add";
        }
        else if (command.contains("delete"))
        {
            property = "market.info.delete";
        }
        else if (command.contains("edit"))
        {
            property = "market.info.edit";
        }
        else if (command.contains("category"))
        {
            property = "market.info.category";
        }
        else if (command.contains("find"))
        {
            property = "market.info.find";
        }
        else if (command.contains("reply"))
        {
            property = "market.info.reply";
        }
        else
        {
            property = "market.info";
        }

        String output = marketProps.getProperty(property);
        log.info(output);
        return output;
    }

    //remove
    @Override
    public void sendMessages(List<Message> messages) {

    }

    private int getMarketUploadedPhotoId(Photo photo, boolean mainPhoto) throws IOException, ClientException, ApiException {
        URL url = getMaxSizedPhotoUrl(photo);
        File file = getPhotoFromServer(url);

        String vkServerUrl = getVkServerUrl(mainPhoto); //api user call +1
        JSONObject object = postPhotoToServer(vkServerUrl, file);

        photo = getVkMarketPhoto(object); //api user call +1

        return photo.getId();
    }

    private URL getMaxSizedPhotoUrl(Photo photo) throws MalformedURLException, ClientException {
        URL url;
        if (photo.getPhoto807() != null){
            url = new URL(photo.getPhoto807());
        }
        else if (photo.getPhoto604() != null) {
            url = new URL(photo.getPhoto604());
        }
        else
            throw new ClientException("Low photo size");
        return url;
    }

    private String getVkServerUrl(boolean mainPhoto) throws ClientException, ApiException {
        return apiClient.photos()
                .getMarketUploadServer(userActor, 177931732)
                .mainPhoto(mainPhoto)
                .execute().getUploadUrl();
    }

    private File getPhotoFromServer(URL url) throws IOException {
        BufferedImage img = ImageIO.read(url);
        File file = new File("downloaded.jpg");
        ImageIO.write(img, "jpg", file);
        FileUtils.copyURLToFile(url, file);
        return file;
    }

    private Photo getVkMarketPhoto(JSONObject object) throws ClientException, ApiException {
        return apiClient.photos()
                .saveMarketPhoto(userActor, object.get("photo").toString(), Integer.parseInt(object.get("server").toString()), object.get("hash").toString())
                .groupId(groupID)
                .cropData(object.get("crop_data").toString())
                .cropHash(object.get("crop_hash").toString())
                .execute().get(0);
    }

    private JSONObject postPhotoToServer(String serverUrl, File photo) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(serverUrl);

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addPart("file", new FileBody(photo));
        httpPost.setEntity(entityBuilder.build());
        HttpResponse postResponse = httpClient.execute(httpPost);

        InputStreamReader reader = new InputStreamReader(postResponse.getEntity().getContent());
        BufferedReader in = new BufferedReader(reader);

        String inputLine;
        StringBuffer outResponse = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            outResponse.append(inputLine);
        }
        reader.close();

        return new JSONObject(outResponse.toString());
    }
}