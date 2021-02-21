package com.panticdamjan.udd.controller;

import com.panticdamjan.udd.dto.SearchDTO;
import com.panticdamjan.udd.dto.UploadBookDTO;
import com.panticdamjan.udd.model.IndexUnit;
import com.panticdamjan.udd.model.Writer;
import com.panticdamjan.udd.repository.BookRepository;
import com.panticdamjan.udd.repository.WriterRepository;
import com.panticdamjan.udd.service.BookService;
import com.panticdamjan.udd.service.Indexer;
import com.panticdamjan.udd.service.QueryBuilder;
import org.apache.lucene.queryparser.classic.ParseException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

    @PostMapping("/search")
    public ResponseEntity<?> searchBook(@RequestBody SearchDTO searchDTO) throws ParseException {

        BoolQueryBuilder query = QueryBuilders.boolQuery();

        if (!searchDTO.getTitle().equals("")){
            org.elasticsearch.index.query.QueryBuilder title = QueryBuilder.buildQuery(searchDTO.getSearchType(),
                    "title", searchDTO.getTitle().toLowerCase());
            switch (searchDTO.getOperatorTitle()){
                case "OR":
                    query.should(title);
                    break;
                case "AND":
                default:
                    query.must(title);
            }
        }

        if (!searchDTO.getWriter().equals("")){
            org.elasticsearch.index.query.QueryBuilder writer = QueryBuilder.buildQuery(searchDTO.getSearchType(),
                    "writer", searchDTO.getWriter().toLowerCase());
            switch (searchDTO.getOperatorWriter()){
                case "OR":
                    query.should(writer);
                    break;
                case "AND":
                default:
                    query.must(writer);
            }
        }

        if (!searchDTO.getGenres().equals("")){
            org.elasticsearch.index.query.QueryBuilder genres = QueryBuilder.buildQuery(searchDTO.getSearchType(),
                    "genres", searchDTO.getGenres().toLowerCase());
            switch (searchDTO.getOperatorGenres()){
                case "OR":
                    query.should(genres);
                    break;
                case "AND":
                default:
                    query.must(genres);
            }
        }

        if (!searchDTO.getKeyWords().equals("")){
            org.elasticsearch.index.query.QueryBuilder keyWords = QueryBuilder.buildQuery(searchDTO.getSearchType(),
                    "keywords", searchDTO.getKeyWords().toLowerCase());
            switch (searchDTO.getOperatorKeyWords()){
                case "OR":
                    query.should(keyWords);
                    break;
                case "AND":
                default:
                    query.must(keyWords);
            }
        }

        if (!searchDTO.getText().equals("")){
            org.elasticsearch.index.query.QueryBuilder text = QueryBuilders.queryStringQuery(searchDTO.getText());
            switch (searchDTO.getOperatorText()){
                case "OR":
                    query.should(text);
                    break;
                case "AND":
                default:
                    query.must(text);
            }
        }

        return new ResponseEntity<>(repository.search(query), HttpStatus.OK);

    }

}
