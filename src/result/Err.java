package utils.result;

import utils.opt.Opt;
import utils.panic.Panic;

import java.util.function.Function;

import static utils.opt.Opt.None;
import static utils.opt.Opt.Some;

public final class Err<T, E> implements Result<T, E> {
    @SuppressWarnings("namedConstructor")
    public static <T, E> Result<T, E> Err(E error) {
        return new Err<>(error);
    }

    public final boolean isOk() {
        return false;
    }

    public final boolean isErr() {
        return true;
    }

    public final boolean contains(T _x) {
        return false;
    }

    public final boolean containsErr(E e) {
        return this.error.equals(e);
    }

    public final Opt<T> ok() {
        return None();
    }

    public final Opt<E> err() {
        return Some(this.error);
    }

    public final <U> Result<U, E> map(Function<? super T, ? extends U> _op) {
        return Err(this.error);
    }

    public final <U> U mapOrElse(
        Function<? super E, ? extends U> fallback,
        Function<? super T, ? extends U> _map
    ) {
        return fallback.apply(this.error);
    }

    public final <F> Result<T, F> mapErr(Function<? super E, ? extends F> op) {
        return Err(op.apply(this.error));
    }

    public final <U> Result<U, E> and(Result<U, E> _res) {
        return Err(this.error);
    }

    public final <U> Result<U, E> andThen(
        Function<? super T, Result<U, E>> _op
    ) {
        return Err(this.error);
    }

    public final Result<T, E> andThenDo(Runnable op) {
        return this;
    }

    public final <F> Result<T, F> or(Result<T, F> res) {
        return res;
    }

    public final <F> Result<T, F> orElse(Function<? super E, Result<T, F>> op) {
        return op.apply(this.error);
    }

    public final Result<T, E> orElseDo(Runnable op) {
        op.run();
        return this;
    }

    public final T unwrapOr(T optb) {
        return optb;
    }

    public final T unwrapOrElse(Function<? super E, ? extends T> op) {
        return op.apply(this.error);
    }

    public final T unwrap() {
        throw new Panic(
            "called `Result.unwrap()` on an `Err` value: " + this.error
        );
    }

    public final T expect(String msg) {
        throw new Panic(msg);
    }

    public final E unwrapErr() {
        return this.error;
    }

    public final E expectErr(String _msg) {
        return this.error;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final var that = (Result<?, ?>)o;
        return that.isErr() && this.error.equals(that.unwrapErr());
    }

    @Override
    public int hashCode() {
        return error.hashCode();
    }

    private Err(E error) {
        this.error = error;
    }

    private final E error;
}
