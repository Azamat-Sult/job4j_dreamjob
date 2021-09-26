package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("lastdayposts", PsqlStore.instOf().findLastDayPosts());
        req.setAttribute("lastdaycandidates", PsqlStore.instOf().findLastDayCandidates());
        req.setAttribute("cities", PsqlStore.instOf().findAllCities());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
