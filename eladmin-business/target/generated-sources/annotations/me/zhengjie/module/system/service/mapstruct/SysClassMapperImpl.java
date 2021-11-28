package me.zhengjie.module.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.module.system.domain.SysClass;
import me.zhengjie.module.system.service.dto.SysClassDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T21:58:00-0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.12 (Eclipse Foundation)"
)
@Component
public class SysClassMapperImpl implements SysClassMapper {

    @Override
    public SysClass toEntity(SysClassDto dto) {
        if ( dto == null ) {
            return null;
        }

        SysClass sysClass = new SysClass();

        sysClass.setId( dto.getId() );
        sysClass.setName( dto.getName() );
        sysClass.setCreateDate( dto.getCreateDate() );
        sysClass.setUpdateDate( dto.getUpdateDate() );
        sysClass.setEnabled( dto.getEnabled() );

        return sysClass;
    }

    @Override
    public SysClassDto toDto(SysClass entity) {
        if ( entity == null ) {
            return null;
        }

        SysClassDto sysClassDto = new SysClassDto();

        sysClassDto.setId( entity.getId() );
        sysClassDto.setName( entity.getName() );
        sysClassDto.setCreateDate( entity.getCreateDate() );
        sysClassDto.setUpdateDate( entity.getUpdateDate() );
        sysClassDto.setEnabled( entity.getEnabled() );

        return sysClassDto;
    }

    @Override
    public List<SysClass> toEntity(List<SysClassDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SysClass> list = new ArrayList<SysClass>( dtoList.size() );
        for ( SysClassDto sysClassDto : dtoList ) {
            list.add( toEntity( sysClassDto ) );
        }

        return list;
    }

    @Override
    public List<SysClassDto> toDto(List<SysClass> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SysClassDto> list = new ArrayList<SysClassDto>( entityList.size() );
        for ( SysClass sysClass : entityList ) {
            list.add( toDto( sysClass ) );
        }

        return list;
    }
}
