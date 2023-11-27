package com.barclays;

import com.barclays.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

@Transactional
public class MemberTestsWithMockHttpRequests {

    @Autowired
    MockMvc mockMvc;
    ResultActions resultActions;



    @Test
    void testGettingAllMembers() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        int expectedLength = 13;

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
    @Rollback
    public void testCreateMember() throws Exception {


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

}
