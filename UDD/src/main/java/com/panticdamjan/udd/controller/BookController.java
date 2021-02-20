package com.panticdamjan.udd.controller;

import com.panticdamjan.udd.dto.UploadBookDTO;
import com.panticdamjan.udd.model.IndexUnit;
import com.panticdamjan.udd.model.Writer;
import com.panticdamjan.udd.repository.BookRepository;
import com.panticdamjan.udd.repository.WriterRepository;
import com.panticdamjan.udd.service.BookService;
import com.panticdamjan.udd.service.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookService bookService;

    @Autowired
    private Indexer indexer;

    @PostMapping("/add")
    public ResponseEntity<?> multiUploadFileModel(@RequestParam("files") MultipartFile files,
                                                       @RequestParam("title") String title,
                                                       @RequestParam("keywords") String keywords,
                                                       @RequestParam("genres") String[] genres,
                                                       @RequestParam("writer") String writer) {

        UploadBookDTO model = new UploadBookDTO();
        model.setKeywords(keywords);
        model.setTitle(title);
        model.setGenres(genres);
        List<MultipartFile> list = new ArrayList();
        list.add(files);
        model.setFiles(list);

        Writer writer1 = writerRepository.findById(writer).get();
        model.setWriter(writer1.getFirstName()+" "+writer1.getLastName());

        try {

            bookService.indexUploadedFile(model);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);

    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {

        String targetFilePath = "/Users/damjanpantic/Desktop/FAX/Master/UDD/UDD/target/classes/files/"+fileName;
        indexer.delete(targetFilePath);

        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {

        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);

    }

}
