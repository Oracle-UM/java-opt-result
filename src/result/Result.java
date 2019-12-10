package utils.result;

import utils.opt.Opt;

import java.util.function.Function;

public interface Result<T, E> {
    boolean isOk();

    boolean isErr();

    boolean contains(T x);

    boolean containsErr(E x);

    Opt<T> ok();

    Opt<E> err();

    <U> Result<U, E> map(Function<? super T, ? extends U> f);

    <U> U mapOrElse(
        Function<? super E, ? extends U> fallback,
        Function<? super T, ? extends U> map
    );

    <F> Result<T, F> mapErr(Function<? super E, ? extends F> op);

    <U> Result<U, E> and(Result<U, E> res);

    <U> Result<U, E> andThen(Function<? super T, Result<U, E>> op);

    Result<T, E> andThenDo(Runnable op);

    <F> Result<T, F> or(Result<T, F> res);

    <F> Result<T, F> orElse(Function<? super E, Result<T, F>> op);

    Result<T, E> orElseDo(Runnable op);

    T unwrapOr(T optb);

    T unwrapOrElse(Function<? super E, ? extends T> op);

    T unwrap();

    T expect(String s);

    E unwrapErr();

    E expectErr(String s);

    boolean equals(Object o);

    int hashCode();
}
