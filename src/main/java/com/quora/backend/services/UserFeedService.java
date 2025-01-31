package com.quora.backend.services;

import com.quora.backend.models.Question;
import com.quora.backend.models.Tag;
import com.quora.backend.models.User;
import com.quora.backend.repositories.QuestionRepository;
import com.quora.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFeedService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getUserFeed(Long userId, int page, int size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Set<Long> tagIds = user.getFollowedTags().stream().map(Tag::getId).collect(Collectors.toSet());

        return questionRepository.findQuestionsByTags(tagIds, PageRequest.of(page, size)).getContent();
    }
}
