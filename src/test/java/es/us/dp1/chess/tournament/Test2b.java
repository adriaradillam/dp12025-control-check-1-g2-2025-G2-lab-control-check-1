package es.us.dp1.chess.tournament;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Test2b {
    @Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

    private String url = "/api/v1/attempts";

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
		.webAppContextSetup(context)
		.apply(SecurityMockMvcConfigurers.springSecurity())
		.build();
	}

    @Test
    @Transactional
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void test2bCanAllAttempts() throws JsonProcessingException, Exception{
        mockMvc.perform(get(url))
			.andExpect(status().isOk())            
            .andExpect(jsonPath("$", hasSize(3)));
    }    

    @Test
    @Transactional
    @WithMockUser(username = "player2", authorities = {"PLAYER"})
    public void test2bCanGetASpecificAttempt() throws JsonProcessingException, Exception{
        mockMvc.perform(get(url+"/300"))
			.andExpect(status().isOk())            
            .andExpect(jsonPath("$.name", is("player 1 - Mate in 1 attempt")))
            .andExpect(jsonPath("$.player.username", is("player1")));
    }    

    @Test
    @Transactional
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void test2bCannotGetNonExistentAttempt() throws JsonProcessingException, Exception{
       mockMvc.perform(get(url+"/474629"))
			.andExpect(status().isNotFound());
    }

    @Test
    @Transactional    
    public void test2bCannotGetAttemptIfYouAreNotPlayer() throws JsonProcessingException, Exception{
       // Perform request as ADMIN
        mockMvc.perform(get(url + "/300")
            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
            .andExpect(status().isForbidden());

        // Perform request as PLAYER
        mockMvc.perform(get(url + "/301")
            .with(user("player1").authorities(new SimpleGrantedAuthority("PLAYER"))))
            .andExpect(status().isOk());
    }
    
}
