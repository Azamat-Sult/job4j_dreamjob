package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("cities", PsqlStore.instOf().findAllCities());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int candidateId = Integer.parseInt(req.getParameter("id"));
        String candidateName = req.getParameter("name");
        int candidateCityId = Integer.parseInt(req.getParameter("city"));
        Candidate candidate = null;
        if (candidateId != 0) {
            candidate = PsqlStore.instOf().findCandidateById(candidateId);
            candidate.setName(candidateName);
            candidate.setCityId(candidateCityId);
            candidate.setRegistered(LocalDate.now());
        } else {
            candidate = new Candidate(candidateId, candidateName, candidateCityId, LocalDate.now());
        }
        PsqlStore.instOf().saveCandidate(candidate);
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
