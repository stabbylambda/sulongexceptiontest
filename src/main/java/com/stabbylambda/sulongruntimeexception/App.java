package com.stabbylambda.sulongruntimeexception;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;

public class App {

    static Context polyglotContext;
    static Source source;

    public static void main(String[] args) throws IOException {
        File bitcode = new File("./src/main/resources/bitcode.bc");
        polyglotContext = Context.newBuilder().allowAllAccess(true).build();
        source = Source.newBuilder("llvm", bitcode).build();
        polyglotContext.eval(source);
        Value bindings = polyglotContext.getBindings("llvm");
        Value throwException = bindings.getMember("throwException");

        /* This works, we will definitely catch the runtime exception as it
           is marshalled across the polyglot context into the correct exception
           here in the Java portion.
         */
        try {
            throwException.execute();
        } catch (PolyglotException e) {
            System.out.println("Caught a C++ runtime exception in Java");
        }

        /* This will never work. Even wrapping in a try/catch, we'll never
        catch any exceptions from the polyglot context.
         */
        try{
            Value catchInCPlusPlus = bindings.getMember("catchInCPlusPlus");
            catchInCPlusPlus.execute();
        } catch (Exception e) {
            // never gonna get here
            System.out.println(e.getMessage());
        }

    }
}
