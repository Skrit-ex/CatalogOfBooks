package com.example.catalogbooks.service;

import com.example.catalogbooks.dto.BookDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksGoogleService {

    private static final String GOOGLE_BOOKS_API = "https://www.googleapis.com/books/v1/volumes";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<BookDto> searchBooks (String query) {
        String url = UriComponentsBuilder.fromUriString(GOOGLE_BOOKS_API)
                .queryParam("q", query)                   //NOTE: поисковый запрос
                .queryParam("maxResults", 5)       //NOTE: ограничение на количество книг
                .queryParam("printType", "books") //NOTE: искать только книги (а не журналы)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class); //NOTE: Отправляется GET‑запрос. Ответ приходит в виде JSON‑строки
        List<BookDto> books = new ArrayList<>();

        try{
            JsonNode root = objectMapper.readTree(response); //NOTE: readTree превращает JSON‑строку в дерево JsonNode
            JsonNode items = root.path("items"); //NOTE: Из корня берётся массив "items" — список книг

            for(JsonNode item : items) {
                JsonNode volumeInfo = item.path("volumeInfo");

                BookDto dto = new BookDto();
                dto.setTitle(volumeInfo.path("title").asText());
                dto.setAuthors(objectMapper.convertValue(volumeInfo.path("authors"), String[].class));
                dto.setPublisher(volumeInfo.path("publisher").asText());
                dto.setPublishedDate(volumeInfo.path("publishedDate").asText());
                dto.setDescription(volumeInfo.path("description").asText());

                // ISBN
                for (JsonNode identifier : volumeInfo.path("industryIdentifiers")) {  //NOTE: В JSON может быть массив industryIdentifiers.
                    if ("ISBN_10".equals(identifier.path("type").asText())) {          //NOTE: Проверяется тип (ISBN_10 или ISBN_13)

                        dto.setIsbn10(identifier.path("identifier").asText());
                    } else if ("ISBN_13".equals(identifier.path("type").asText())) {
                        dto.setIsbn13(identifier.path("identifier").asText());
                    }
                }
                // Thumbnail
                dto.setThumbnail(volumeInfo.path("imageLinks").path("thumbnail").asText());

                books.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
