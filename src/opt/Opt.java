package utils.opt;

import utils.panic.Panic;
import utils.result.Result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static utils.result.Err.Err;
import static utils.result.Ok.Ok;

public final class Opt<T> {
    public static <T> Opt<T> Some(T value) {
        return new Opt<>(value);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Opt<T> None() {
        return (Opt<T>)None;
    }

    public static <T> Opt<T> fromOptional(Optional<T> opt) {
        return opt.isEmpty() ? None() : Some(opt.get());
    }

    public final boolean isSome() {
        return this.value != null;
    }

    public final boolean isNone() {
        return this.value == null;
    }

    public final boolean contains(T x) {
        return (this.value != null) && (this.value.equals(x));
    }

    public final T expect(String msg) {
        if (this.value != null) {
            return this.value;
        }
        throw new Panic(msg);
    }

    public final T unwrap() {
        if (this.value != null) {
            return this.value;
        }
        throw new Panic(
            "called `Option.unwrap()` on a `None` value"
        );
    }

    public final T unwrapOr(T def) {
        return this.value != null ? this.value : def;
    }

    public final T unwrapOrElse(Supplier<? extends T> f) {
        return this.value != null ? this.value : f.get();
    }

    public final <U> Opt<U> map(Function<? super T, ? extends U> f) {
        return this.value != null ? Some(f.apply(this.value)) : None();
    }

    public final <U> U mapOr(U def, Function<? super T, ? extends U> f) {
        return this.value != null ? f.apply(this.value) : def;
    }

    public final <U> U mapOrElse(
        Supplier<? extends U> def,
        Function<? super T, ? extends U> f
    ) {
        return this.value != null ? f.apply(this.value) : def.get();
    }

    public final <E> Result<T, E> okOr(E err) {
        return this. value != null ? Ok(this.value) : Err(err);
    }

    public final <E> Result<T, E> okOrElse(Supplier<? extends E> err) {
        return this.value != null ? Ok(this.value) : Err(err.get());
    }

    public final <U> Opt<U> and(Opt<U> optb) {
        return this.value != null ? optb : None();
    }

    public final <U> Opt<U> andThen(Function<? super T, Opt<U>> f) {
        return this.value != null ? f.apply(this.value) : None();
    }

    public final Opt<T> andThenDo(Runnable op) {
        if (this.value != null) {
            op.run();
        }
        return this;
    }

    public final Opt<T> filter(Predicate<? super T> predicate) {
        return (this.value != null) && (predicate.test(this.value))
            ? Some(this.value)
            : None();
    }

    public final Opt<T> or(Opt<T> optb) {
        return this.value != null ? this : optb;
    }

    public final Opt<T> orElse(Supplier<Opt<T>> f) {
        return this.value != null ? this : f.get();
    }

    public final Opt<T> orElseDo(Runnable op) {
        if (this.value == null) {
            op.run();
        }
        return this;
    }

    public final Opt<T> xor(Opt<T> optb) {
        final var some_b = optb.value != null;
        if (this.value != null) {
            return some_b ? None() : Some(this.value);
        }
        return (some_b) ? Some(optb.value) : None();
    }

    public final T getOrInsert(T v) {
        if (this.value == null) {
            this.value = v;
        }
        return this.value;
    }

    public  final T getOrInsertWith(Supplier<? extends T> f) {
        if (this.value == null) {
            this.value = f.get();
        }
        return this.value;
    }

    public final Opt<T> replace(T value) {
        final var old_value = this.value;
        this.value = value;
        return old_value != null ? Some(old_value) : None();
    }

    public final void expectNone(String msg) {
        if (this.value != null) {
            throw new Panic(msg);
        }
    }

    public final void unwrapNone() {
        if (this.value != null) {
            throw new Panic(
                "called `Option.unwrapNone()` on a `Some` value: " + this.value
            );
        }
    }

    public final Optional<T> toOptional() {
        return this.value != null ? Optional.of(this.value) : Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        return Objects.equals(this.value, ((Opt<?>)o).value);
    }

    @Override
    public int hashCode() {
        return this.value != null ? this.value.hashCode() : 0;
    }

    private Opt() {
        this.value = null;
    }

    private Opt(T value) {
        this.value = value;
    }

    public static final Opt<?> None = new Opt<>();
    private T value;
}
