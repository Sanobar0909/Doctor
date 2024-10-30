package uz.pdp.Doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.Doctor.model.Article;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, String> {
    List<Article> findAllByReytingStar(float star);
}
