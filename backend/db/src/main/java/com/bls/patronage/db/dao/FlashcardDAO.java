package com.bls.patronage.db.dao;

import com.bls.patronage.db.exception.DataAccessException;
import com.bls.patronage.db.model.Flashcard;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterMapper(FlashcardMapper.class)
abstract public class FlashcardDAO {

    @SqlQuery("select id,question,answer,deckID from flashcards where id = :id")
    abstract Flashcard get(@Bind("id") UUID id);

    @SqlQuery("select id,question,answer,deckID from flashcards where deckId = :deckId")
    public abstract List<Flashcard> getAllFlashcards(@Bind("deckId") UUID deckId);

    @GetGeneratedKeys
    @SqlUpdate("insert into flashcards values (:id, :question, :answer, :deckID)")
    public abstract UUID createFlashcard(@BindBean Flashcard flashcard);

    @SqlUpdate("update flashcards set question = :question, answer = :answer where id = :id")
    public abstract void updateFlashcard(@BindBean Flashcard flashcard);

    @SqlUpdate("delete from flashcards where id = :id")
    public abstract void deleteFlashcard(@Bind("id") UUID id);

    public Flashcard getFlashcardById(UUID id) {
        Optional<Flashcard> flashcard = Optional.ofNullable(get(id));
        return flashcard.orElseThrow(() -> new DataAccessException("There is no flashcard with specified id"));
    }
}
