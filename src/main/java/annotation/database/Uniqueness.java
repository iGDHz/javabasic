package annotation.database;

import annotation.database.Constraints;

public @interface Uniqueness {
    Constraints constraints()
            default @Constraints(unique = true);
}
