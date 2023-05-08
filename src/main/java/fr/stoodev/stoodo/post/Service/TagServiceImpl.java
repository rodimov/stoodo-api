package fr.stoodev.stoodo.post.Service;

import fr.stoodev.stoodo.post.DTO.TagCreationDTO;
import fr.stoodev.stoodo.post.Entity.Tag;
import fr.stoodev.stoodo.post.Repository.TagRepository;
import fr.stoodev.stoodo.post.Service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Tag create(TagCreationDTO tagCreationDTO) {
        Tag tag = this.modelMapper.map(tagCreationDTO, Tag.class);

        var tagOptional = getOneByTag(tag.getTag());

        return tagOptional.orElseGet(() -> this.tagRepository.save(tag));

    }

    @Override
    public Optional<Tag> getOneByTag(String tag) {
        return this.tagRepository.findByTag(tag);
    }

    @Override
    public Page<Tag> getList(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return this.tagRepository.findAll(pr);
    }
}
