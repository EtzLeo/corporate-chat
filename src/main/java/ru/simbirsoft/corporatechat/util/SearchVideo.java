package ru.simbirsoft.corporatechat.util;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SearchVideo {

    private String apikey;

    private YouTube youTube;

    public SearchVideo(@Value("${youtube.apikey}") String apikey) {
        if (youTube == null) {
            youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {

                }
            }).setApplicationName("corporate-chat").build();
        }
        this.apikey = apikey;
    }

    public String getChannelId(String channel) throws IOException {
        String channelId = null;
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        search.setQ(channel);
        search.setType("channel");
        search.setMaxResults(10L);

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        if (searchResultList != null) {
            Iterator<SearchResult> iteratorSearchResult = searchResultList.iterator();
            if (iteratorSearchResult.hasNext()) {
                while (iteratorSearchResult.hasNext()) {
                    SearchResult singleChannel = iteratorSearchResult.next();
                    if (singleChannel.getId().getKind().equals("youtube#channel") && singleChannel.getSnippet().getTitle().equals(channel)) {
                        channelId = singleChannel.getId().getChannelId();
                        break;
                    }
                }
            }
        }
        return channelId;
    }

    public List<Video> getVideos(String videoName, String channelId, Long resultCount, Boolean sort) throws IOException {
        List<Video> videosList = new ArrayList<>();
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setKey(apikey);
        if (!videoName.isEmpty()) {
            search.setQ(videoName);
        }
        search.setType("video");
        if (sort) {
            search.setOrder("date");
        }
        if (!channelId.isEmpty()) {
            search.setChannelId(channelId);
        }
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/publishedAt)");
        if (resultCount > 0) {
            search.setMaxResults(resultCount);
        }

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();

        if (searchResultList != null) {
            Iterator<SearchResult> iteratorSearchResult = searchResultList.iterator();
            if (iteratorSearchResult.hasNext()) {
                while (iteratorSearchResult.hasNext()) {
                    SearchResult singleVideo = iteratorSearchResult.next();
                    ResourceId rId = singleVideo.getId();
                    if (rId.getKind().equals("youtube#video")) {

                        YouTube.Videos.List youtubeVideos = youTube.videos().list("id,snippet,player,contentDetails,statistics").setId(rId.getVideoId());
                        youtubeVideos.setKey(apikey);
                        youtubeVideos.setId(rId.getVideoId());

                        VideoListResponse videoListResponse = youtubeVideos.execute();

                        Video video = null;
                        if (!videoListResponse.getItems().isEmpty()) {
                            video = videoListResponse.getItems().get(0);
                        }
                        videosList.add(video);
                    }
                }
            }
        }
    return videosList;
    }

    public Map<String, String> getComments(Video video, Long resultCount) throws IOException {
        Map<String, String> comments = new HashMap<>();

        CommentThreadListResponse commentListResponse = youTube
                .commentThreads()
                .list("snippet")
                .setKey(apikey)
                .setVideoId(video.getId())
                .setMaxResults(resultCount)
                .execute();

        List<CommentThread> commentThreads = commentListResponse.getItems();
        CommentSnippet snippet;
        for (CommentThread commentThread : commentThreads) {
            snippet = commentThread.getSnippet().getTopLevelComment().getSnippet();
            comments.put(snippet.getAuthorDisplayName(), snippet.getTextDisplay());
        }
        return comments;
    }
}
