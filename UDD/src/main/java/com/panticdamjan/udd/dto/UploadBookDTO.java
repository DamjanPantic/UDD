package com.panticdamjan.udd.dto;

import com.panticdamjan.udd.enumeration.Genres;
import com.panticdamjan.udd.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadBookDTO {

    private String title;
    private String keywords;
    private String[] genres;
    private List<MultipartFile> files;
    private String writer;
}
