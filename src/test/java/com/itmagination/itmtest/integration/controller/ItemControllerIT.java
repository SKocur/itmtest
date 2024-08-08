package com.itmagination.itmtest.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.itmagination.itmtest.integration.AbstractIntegrationTestBase;
import com.itmagination.itmtest.common.SearchResult;
import com.itmagination.itmtest.controller.requests.CreateItemRequest;
import com.itmagination.itmtest.controller.requests.UpdateItemRequest;
import com.itmagination.itmtest.item.ItemDto;
import com.itmagination.itmtest.item.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemControllerIT extends AbstractIntegrationTestBase {

    @Test
    void shouldDoCRUDOperations() throws Exception {
        Set<CreateItemRequest> request = generateCreateItemRequests();

        mockMvc.perform(
                post("/api/v1/items/create-many")
                        .with(withUser(adminUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        String rawResponse = mockMvc.perform(
                get("/api/v1/items/search")
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        SearchResult<ItemDto> searchResult = mapToSearchResult(rawResponse);

        Assertions.assertEquals(4, searchResult.totalElements());
        Assertions.assertEquals(1, searchResult.totalPages());


        rawResponse = mockMvc.perform(
                get("/api/v1/items/search")
                        .param("type", "PC_GAME")
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        searchResult = mapToSearchResult(rawResponse);
        Assertions.assertEquals(2, searchResult.totalElements());

        ItemDto witcherItem = searchResult.content().stream().filter(item -> item.name().equals("Witcher 3"))
                .findAny().orElseThrow();

        rawResponse = mockMvc.perform(
                get("/api/v1/items/search")
                        .param("id", String.format("%d", witcherItem.id()))
                        .param("name", "Witcher 3")
                        .param("type", "PC_GAME")
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        searchResult = mapToSearchResult(rawResponse);
        Assertions.assertEquals(1, searchResult.totalElements());

        UpdateItemRequest updateItemRequest = new UpdateItemRequest(
                "Witcher 3 GOTY Edition",
                "RPG GOTY edition game",
                ItemType.PC_GAME
        );

        mockMvc.perform(
                put(String.format("/api/v1/items/%d", witcherItem.id()))
                        .with(withUser(adminUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateItemRequest))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        rawResponse = mockMvc.perform(
                get("/api/v1/items/search")
                        .param("id", String.format("%d", witcherItem.id()))
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        searchResult = mapToSearchResult(rawResponse);

        Assertions.assertEquals("Witcher 3 GOTY Edition", searchResult.content().getFirst().name());

        mockMvc.perform(
                delete(String.format("/api/v1/items/%d", witcherItem.id()))
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        rawResponse = mockMvc.perform(
                get("/api/v1/items/search")
                        .param("id", String.format("%d", witcherItem.id()))
                        .with(withUser(adminUser))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        searchResult = mapToSearchResult(rawResponse);

        Assertions.assertEquals(0, searchResult.totalElements());


    }

    @Test
    void shouldNotCreateMalformedItem() throws Exception {
        CreateItemRequest wrongItemRequest = new CreateItemRequest(
                "LEGO",
                "",
                ItemType.TOY
        );

        mockMvc.perform(
                post("/api/v1/items")
                        .with(withUser(adminUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongItemRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Set<CreateItemRequest> generateCreateItemRequests() {
        CreateItemRequest item1 = new CreateItemRequest(
                "LEGO",
                "Great lego kit for everyone",
                ItemType.TOY
        );
        CreateItemRequest item2 = new CreateItemRequest(
                "Chair Jarv",
                "Office chair",
                ItemType.FURNITURE
        );
        CreateItemRequest item3 = new CreateItemRequest(
                "Cyberpunk 2077",
                "FPS game",
                ItemType.PC_GAME
        );
        CreateItemRequest item4 = new CreateItemRequest(
                "Witcher 3",
                "RPG game",
                ItemType.PC_GAME
        );

        return Set.of(item1, item2, item3, item4);
    }

    private SearchResult<ItemDto> mapToSearchResult(String rawResponse) throws JsonProcessingException {
        return objectMapper.readValue(rawResponse, new TypeReference<>() {
        });
    }
}
