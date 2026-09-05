package app.config.hibernate;

import app.entities.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Quote.class);
        configuration.addAnnotatedClass(Exercise.class);
        configuration.addAnnotatedClass(ExerciseData.class);
        configuration.addAnnotatedClass(Split.class);
        configuration.addAnnotatedClass(Workout.class);
        configuration.addAnnotatedClass(WorkoutExercise.class);
    }
}