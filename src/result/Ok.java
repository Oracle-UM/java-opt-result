package utils.result;

import utils.opt.Opt;
import utils.panic.Panic;

import java.util.function.Function;

import static utils.opt.Opt.None;
import static utils.opt.Opt.Some;

public final class Ok<T, E> implements Result<T, E> {
    @SuppressWarnings("constructorName")
    public static <T, E> Result<T, E> Ok(T value) {
        return new Ok<>(value);
    }

    public final boolean isOk() {
        return true;
    }

    public final boolean isErr() {
        return false;
    }

    public final boolean contains(T x) {
        return this.value.equals(x);
    }

    public final boolean containsErr(E _e) {
        return false;
    }

    public final Opt<T> ok() {
        return Some(this.value);
    }

    public final Opt<E> err() {
        return None();
    }

    public final <U> Result<U, E> map(Function<? super T, ? extends U> op) {
        return Ok(op.apply(this.value));
    }

    public final <U> U mapOrElse(
        Function<? super E, ? extends U> _fallback,
        Function<? super T, ? extends U> map
    ) {
        return map.apply(this.value);
    }

    public final <F> Result<T, F> mapErr(
        Function<? super E, ? extends F> _op
    ) {
        return Ok(this.value);
    }

    public final <U> Result<U, E> and(Result<U, E> res) {
        return res;
    }

    public final <U> Result<U, E> andThen(
        Function<? super T, Result<U, E>> op
    ) {
        return op.apply(this.value);
    }

    public final Result<T, E> andThenDo(Runnable op) {
        op.run();
        return this;
    }

    public final <F> Result<T, F> or(Result<T, F> _res) {
        return Ok(this.value);
    }

    public final <F> Result<T, F> orElse(
        Function<? super E, Result<T, F>> _op
    ) {
        return Ok(this.value);
    }

    public final Result<T, E> orElseDo(Runnable op) {
        return this;
    }

    public final T unwrapOr(T _optb) {
        return this.value;
    }

    public final T unwrapOrElse(Function<? super E, ? extends T> _op) {
        return this.value;
    }

    public final T unwrap() {
        return this.value;
    }

    public final T expect(String s) {
        return this.value;
    }

    public final E unwrapErr() {
        throw new Panic(
            "called `Result.unwrapErr()` on an `Ok` value: " + this.value
        );
    }

    public final E expectErr(String msg) {
        throw new Panic(msg);
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
        return that.isOk() && this.value.equals(that.unwrap());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    private Ok(T value) {
        this.value = value;
    }

    private final T value;
}
