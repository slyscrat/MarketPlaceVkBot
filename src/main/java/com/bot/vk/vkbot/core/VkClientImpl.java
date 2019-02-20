package com.bot.vk.vkbot.core;

import com.bot.vk.vkbot.core.client.VkClient;

import com.bot.vk.vkbot.service.ItemService;

import com.sun.xml.internal.ws.api.message.Attachment;

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
import com.vk.api.sdk.objects.photos.responses.GetMarketUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MarketUploadResponse;
import com.vk.api.sdk.queries.market.MarketAddQuery;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.var;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bot.vk.vkbot.Entity.Item;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
@Log4j2
public class VkClientImpl implements VkClient {

    private final VkApiClient apiClient = new VkApiClient(HttpTransportClient.getInstance());
    private GroupActor groupActor;
    private UserActor userActor;

    private final ItemService itemService;

    @Value("${bot.group.id}")
    private int groupID;

    @Value("${bot.user.id}")
    private int userID;

    @Value("${bot.group.client_secret}")
    private String groupToken;

    @Value("${bot.user.client_secret}")
    private String userToken;

    @Autowired
    public VkClientImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostConstruct
    public void init() {
        this.groupActor = new GroupActor(groupID, groupToken);
        this.userActor = new UserActor(userID, userToken);
    }

    @Override
    public void sendMessage(String message, int userId) {
        Random random = new Random();
        try {
            this.apiClient.messages().send(groupActor).message(message).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    @Override
    public List<Message> readMessages() {
        List<Message> result = new ArrayList<>();
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

    @Override
    public void replyForMessages(List<Message> messages) {
        String body;
        List<MessageAttachment> list;
        for (Message item : messages) {
            body = item.getBody();
            list = item.getAttachments();
            //to do проверка на вложения
            if (body.equals("Начать")){
                sendMessage("Hello", item.getUserId());
            }
            else if (body.contains("/add"))
            {
                //   /add name description categoryId price
                //обработка + работа со строками
                String[] params = body.split(" ");

                postProduct(params[1], params[2], Integer.parseInt(params[3]), Double.parseDouble(params[4]) , list.get(0).getPhoto());
                sendMessage("You add ", item.getUserId());
            }
            else if (body.contains("/find")){
                //https://vk.com/club177931732?w=product-177931732_3049472%2Fquery
                String[] params = body.split(" ");
                sendMessage("You find: \n https://vk.com/club177931732?w=product-177931732_" + Integer.parseInt(params[1]) + "%2Fquery", item.getUserId());
            }
            else sendMessage("Don't understand you, try again", item.getUserId());
        }
    }

    @Override
    public List<Message> sendMessagesRestrictor(List<Message> messages) {
        return null;
    }

    @Override
    public void postProduct(Long userId, String name, String description, Long type, Float price, Photo photo) {
        try {
            int photoId = getMarketUploadedPhotoId(photo, true); //api user call +2
            this.itemService.addItem(new Item(userId, name, description, photoId, price, type));
            apiClient.market().add(userActor, -1*groupID, name, description, categoryId, price, photoId).execute();
        } catch (IOException | ClientException | ApiException e) {
            log.error(e);
        }
    }


    @Override
    public void deleteProduct(Long id) {
        try{
            this.itemService.delete(id);
            //MarketAddQuery a = new MarketAddQuery(apiClient, actor, 133773509);
        }
        catch(Exception ex){
            log.error(ex);
        }
      
    }

    @Override
    public void banUser(int id) {
        //ban
    }

    @Override
    public void unBanUser(int id) {
        //unban
    }

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