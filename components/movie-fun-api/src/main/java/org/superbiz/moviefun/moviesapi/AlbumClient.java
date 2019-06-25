package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class AlbumClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String URL;

    RestOperations restTemplate;

    public AlbumClient(String albumsUrl, RestOperations restOperations) {
        URL = albumsUrl;
        restTemplate = restOperations;
    }

    public void addAlbum(AlbumInfo album) {
        logger.info("URL: {}, PUT, addAlbum", URL);
        restTemplate.put(URL, album);
        logger.info("rest call succeeded");
    }

    public List<AlbumInfo> getAlbums() {
        return restTemplate.getForObject(URL, List.class);
    }

    public AlbumInfo find(long albumId) {
        return restTemplate.getForObject(URL + "/" + String.valueOf(albumId), AlbumInfo.class);
    }
}
