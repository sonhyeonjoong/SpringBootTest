package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.BoardJooqRepository;
import com.example.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nu.studer.sample.tables.records.BoardMemberRecord;
import nu.studer.sample.tables.records.BoardRecord;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final ReplyRepository replyRepository;
    private final BoardRepository repository;
    private final BoardJooqRepository boardJooqRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

        //Page<Object[]> result = repository.getBoardWithReplyCount(
        //        pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }
    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        //댓글 부터 삭제 후 게시물 삭제, 하나의 Transaction으로 처리
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);
    }
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getReferenceById(boardDTO.getBno()); //해당 entity 휙득, 필요한 순간까지 로딩 지연

        board.changeTitle(boardDTO.getTitle()); //해당 entity 변경
        board.changeContent(boardDTO.getContent());

        repository.save(board); // repository를 이용해 DB에 저장
    }

    @Override
    public List jooqGet(Long bno){
        List list;
        Result<Record3<BoardRecord, BoardMemberRecord, Integer>> result = boardJooqRepository.findByBno(bno);

        list = result.getValues(0);

        return list;
    }

    @Override
    public Result<Record3<BoardRecord, BoardMemberRecord, Integer>> jooqGet(){

        Result<Record3<BoardRecord, BoardMemberRecord, Integer>> result = boardJooqRepository.getBoardList();

        return result;
    }
}
