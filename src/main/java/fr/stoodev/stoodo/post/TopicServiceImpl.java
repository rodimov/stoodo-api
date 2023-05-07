package fr.stoodev.stoodo.post;

import fr.stoodev.stoodo.post.DTO.TopicCreationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Topic create(TopicCreationDTO topicCreationDTO) {
        Topic topic = this.modelMapper.map(topicCreationDTO, Topic.class);
        var topicOptional = getOneByTopic(topic.getTopic());
        return topicOptional.orElseGet(() -> this.topicRepository.save(topic));

    }

    @Override
    public Optional<Topic> getOneByTopic(String topic) {
        return this.topicRepository.findByTopic(topic);
    }

    @Override
    public Optional<Topic> getOneById(Long topicId) {
        return this.topicRepository.findById(topicId);
    }

    @Override
    public Page<Topic> getList(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return this.topicRepository.findAll(pr);
    }
}
