package com.example.board.repository;

        import com.example.board.dto.BoardDTO;
        import lombok.RequiredArgsConstructor;
        import nu.studer.sample.tables.Board;
        import nu.studer.sample.tables.BoardMember;
        import nu.studer.sample.tables.Member;
        import nu.studer.sample.tables.Reply;
        import nu.studer.sample.tables.records.BoardMemberRecord;
        import nu.studer.sample.tables.records.BoardRecord;
        import org.jooq.DSLContext;
        import org.jooq.Record3;
        import org.jooq.Result;
        import org.jooq.impl.DSL;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardJooqRepository {
    private final DSLContext dsl;

    private Board board = Board.BOARD;
    private BoardMember boardmember = BoardMember.BOARD_MEMBER;
    private Reply reply = Reply.REPLY;

    
    public Result<Record3<BoardRecord, BoardMemberRecord, Integer>> findByBno(Long bno) {
        return dsl.select(board, boardmember, DSL.count())
                .from(board
                        .leftJoin(boardmember)
                        .on(boardmember.EMAIL
                                .eq(board.WRITER_EMAIL))
                        .leftJoin(reply)
                        .on(reply.BOARD_BNO
                                .eq(board.BNO)))
                .where(board.BNO.eq(bno))
                .groupBy(board, boardmember)
                .fetch();
    }

    public Result<Record3<BoardRecord, BoardMemberRecord, Integer>> getBoardList() {
        return dsl.select(board, boardmember, DSL.count())
                .from(board
                        .leftJoin(boardmember)
                        .on(boardmember.EMAIL
                                .eq(board.WRITER_EMAIL))
                        .leftJoin(reply)
                        .on(reply.BOARD_BNO
                                .eq(board.BNO)))
                .groupBy(board, boardmember)
                .orderBy(board.BNO.asc())
                .fetch();
    }
}
