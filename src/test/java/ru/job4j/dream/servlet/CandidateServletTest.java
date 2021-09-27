package ru.job4j.dream.servlet;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/*@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)*/
public class CandidateServletTest {

    /*@Test
    public void whenEditCandidate() throws ServletException, IOException {
        Store store = MemStore.instOf();
        store.saveCandidate(new Candidate(1, "name"));

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("1");
        PowerMockito.when(req.getParameter("name")).thenReturn("new name");

        new CandidateServlet().doPost(req, resp);

        Candidate result = store.findAllCandidates().iterator().next();
        assertEquals(result.getId(), 1);
        assertEquals(result.getName(), "new name");
    }

    @Test
    public void whenCreateCandidate() throws ServletException, IOException {
        Store store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("0");
        PowerMockito.when(req.getParameter("name")).thenReturn("name");

        new CandidateServlet().doPost(req, resp);

        Candidate result = store.findAllCandidates().iterator().next();
        assertEquals(result.getId(), 1);
        assertEquals(result.getName(), "name");
    }*/
}