package me.zhengjie.module.business.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.module.business.domain.Question;
import me.zhengjie.module.business.service.dto.QuestionDto;
import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.module.system.service.dto.SysClassSmallDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T21:58:00-0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.12 (Eclipse Foundation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question toEntity(QuestionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Question question = new Question();

        question.setCreateBy( dto.getCreateBy() );
        question.setUpdateBy( dto.getUpdateBy() );
        question.setCreateTime( dto.getCreateTime() );
        question.setUpdateTime( dto.getUpdateTime() );
        question.setId( dto.getId() );
        question.setContent( dto.getContent() );
        question.setTid( dto.getTid() );
        question.setEnabled( dto.getEnabled() );
        question.setAnswer( dto.getAnswer() );
        question.setCids( sysClassSmallDtoToSysClass( dto.getCids() ) );

        return question;
    }

    @Override
    public QuestionDto toDto(Question entity) {
        if ( entity == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        questionDto.setCreateBy( entity.getCreateBy() );
        questionDto.setUpdateBy( entity.getUpdateBy() );
        questionDto.setCreateTime( entity.getCreateTime() );
        questionDto.setUpdateTime( entity.getUpdateTime() );
        questionDto.setId( entity.getId() );
        questionDto.setCids( sysClassToSysClassSmallDto( entity.getCids() ) );
        questionDto.setContent( entity.getContent() );
        questionDto.setTid( entity.getTid() );
        questionDto.setEnabled( entity.getEnabled() );
        questionDto.setAnswer( entity.getAnswer() );

        return questionDto;
    }

    @Override
    public List<Question> toEntity(List<QuestionDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Question> list = new ArrayList<Question>( dtoList.size() );
        for ( QuestionDto questionDto : dtoList ) {
            list.add( toEntity( questionDto ) );
        }

        return list;
    }

    @Override
    public List<QuestionDto> toDto(List<Question> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<QuestionDto> list = new ArrayList<QuestionDto>( entityList.size() );
        for ( Question question : entityList ) {
            list.add( toDto( question ) );
        }

        return list;
    }

    protected SysClass sysClassSmallDtoToSysClass(SysClassSmallDto sysClassSmallDto) {
        if ( sysClassSmallDto == null ) {
            return null;
        }

        SysClass sysClass = new SysClass();

        if ( sysClassSmallDto.getId() != null ) {
            sysClass.setId( sysClassSmallDto.getId().longValue() );
        }
        sysClass.setName( sysClassSmallDto.getName() );

        return sysClass;
    }

    protected SysClassSmallDto sysClassToSysClassSmallDto(SysClass sysClass) {
        if ( sysClass == null ) {
            return null;
        }

        SysClassSmallDto sysClassSmallDto = new SysClassSmallDto();

        if ( sysClass.getId() != null ) {
            sysClassSmallDto.setId( sysClass.getId().intValue() );
        }
        sysClassSmallDto.setName( sysClass.getName() );

        return sysClassSmallDto;
    }
}
