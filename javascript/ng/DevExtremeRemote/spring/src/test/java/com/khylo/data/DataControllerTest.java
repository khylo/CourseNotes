package com.khylo.data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest // Loads the full Spring context
@AutoConfigureMockMvc // Configures MockMvc for testing controllers
public class DataControllerTest {

  @Autowired
  private MockMvc mockMvc; // Injects MockMvc

  @Test
  public void testGetAllData() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/data"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      // Add more assertions to check the content of the response
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray()) // Check if 'content' is an array
      .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber()); // Check if 'totalElements' is a number
  }


  @Test
  public void testGetPaginatedData() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/data?page=0&size=10"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
      .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(10)) // Check page size
      .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber());
  }

  @Test
  public void testGetPaginatedDataWithSorting() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/data?page=0&size=10&sortField=name&sortOrder=asc"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
      .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber());
    // Additional assertions to check if data is sorted correctly
  }

  @Test
  public void testGetPaginatedDataWithFilter() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/data?page=0&size=10&filterField=name&filterValue=Item"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
      .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber());
    // Add assertions to verify that filtering is working
  }

  @Test
  public void testGetPaginatedDataWithFilterAndSorting() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/data?page=0&size=10&filterField=name&filterValue=Item&sortField=id&sortOrder=asc"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
      .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").isNumber());
    // Add assertions to verify that filtering and sorting are working
  }

}
