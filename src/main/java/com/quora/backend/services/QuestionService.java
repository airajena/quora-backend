package com.quora.backend.services;

import com.quora.backend.dtos.QuestionDTO;
import com.quora.backend.models.Question;
import com.quora.backend.models.Tag;
import com.quora.backend.models.User;
import com.quora.backend.repositories.QuestionRepository;
import com.quora.backend.repositories.TagRepository;
import com.quora.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Question> getQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Question createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());

        Optional<User> user = userRepository.findById(questionDTO.getUserId());
        user.ifPresent(question::setUser);

        Set<Tag> tags = questionDTO.getTagIds().stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        question.setTags(tags);

        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
