package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

public class DeleteFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidate candidate = Store.instOf().findCandidateById(Integer.parseInt(req.getParameter("id")));
        String photoFileName = candidate.getPhoto();
        if (!"noPhoto.jpg".equals(photoFileName)) {
            File photoFile = new File("c:\\images\\" + File.separator + photoFileName);
            photoFile.delete();
            candidate.setPhoto("noPhoto.jpg");
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

}
