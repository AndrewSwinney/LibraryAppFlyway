package com.barclays;

import com.barclays.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

@Transactional
class MemberTestsWithMockHttpRequests {

    @Autowired
    MockMvc mockMvc;
    ResultActions resultActions;


    @Test
    void testGettingAllMembers() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 17;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Member[] members = mapper.readValue(contentAsString, Member[].class);

        assertAll("Testing members endpoint",
                () -> assertEquals(expectedLength, members.length),
                () -> assertEquals("Andrew", members[0].getName()),
                () -> assertEquals("Kim", members[1].getName()));
    }

    @Test
    void testGettingMemberByFilteringName() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 1;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members?filter=Andrew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Member[] members = mapper.readValue(contentAsString, Member[].class);

        assertAll("Testing members endpoint",
                () -> assertEquals(expectedLength, members.length),
                () -> assertEquals("Andrew", members[0].getName()));
    }

    @Test
    void testGettingMemberByFilteringByEmail() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 1;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members?emailAddress=andrews@email.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Member[] members = mapper.readValue(contentAsString, Member[].class);

        assertAll("Testing members endpoint",
                () -> assertEquals(expectedLength, members.length),
                () -> assertEquals("andrews@email.com", members[0].getEmailAddress()));
    }

    @Test
    void testGettingMemberByFilteringByMemberId() throws Exception {


        int expectedLength = 1;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members/37")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();


        List<String> members = new ArrayList<>();

        members.add(contentAsString);

        assertAll("Testing members endpoint",
                () -> assertEquals(expectedLength, members.size()));
    }


    @Test
    @Rollback
     void testCreateMember() throws Exception {


        ObjectMapper mapper;


        Member member = new Member();
        member.setName("Luke");
        member.setEmailAddress("luke@email.com");

        mapper = new ObjectMapper();

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .content(mapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        member = mapper.readValue(contentAsString, Member.class);

        assertEquals("Luke", member.getName());
    }

    @Test
    @Rollback
     void testUpdateMember() throws Exception {


        ObjectMapper mapper;


        Member member = new Member();
        member.setId(37L);
        member.setName("Pete");
        member.setEmailAddress("pete@email.com");


        mapper = new ObjectMapper();

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put("/members")
                        .content(mapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        member = mapper.readValue(contentAsString, Member.class);

        assertEquals("Pete", member.getName());
    }

    @Test
    @Rollback
     void testDeleteMember() throws Exception {


        ObjectMapper mapper;
        mapper = new ObjectMapper();


        Member member = new Member();
        member.setId(37L);
        member.setName("Pete");
        member.setEmailAddress("pete@email.com");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .content(mapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/members")
                        .content(mapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        System.out.println("Content as string" + contentAsString);

        assertTrue(contentAsString.equals(""));
    }

    @Test
    void testGettingMembersWithBooks() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 4;


        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members/with-books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Member[] members = mapper.readValue(contentAsString, Member[].class);

        assertAll("Testing get members with books endpoint",
                () -> assertEquals(expectedLength, members.length));
    }

    @Test
    void testGettingMembersWithMovies() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 3;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/members/with-movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Member[] members = mapper.readValue(contentAsString, Member[].class);

        assertAll("Testing get members with movies endpoint",
                () -> assertEquals(expectedLength, members.length));
    }

    @Test
    void memberAllArgsConstructorTest() {
        Member member = new Member("Andrew", "andrew@email.com");
        assertEquals("Andrew", member.getName());
        assertEquals("andrew@email.com", member.getEmailAddress());
    }

    @Test
    @Rollback
     void testAssignBookToMember() throws Exception {
        long memberId = 43L;
        long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put("/members/" + memberId + "/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
     void testAssignMovieToMember() throws Exception {
        long memberId = 43L;
        long movieId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.put("/members/" + memberId + "/movies/" + movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
