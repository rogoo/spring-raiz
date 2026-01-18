package br.rosa.pagin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.model.Post;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

	@Mapping(target = "idAuthor", source = "author.id")
	PostDTO postToPostDTO(Post post);

	List<PostDTO> postToPostDTO(List<Post> post);
}
