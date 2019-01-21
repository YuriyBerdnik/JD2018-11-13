package by.it.denisova.jd03_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class C_Init {

    private static Statement statement;
    private static Config config;

    static void createDatabase(Config config) {
        C_Init.config = config;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(config.getDatabaseURL(),config.getUserName(),config.getPassword())) {
            statement = connection.createStatement();
            createDatabaseAndTables();
            addRole("admin");
            addRole("user");
            addRole("guest");

            addUser("katia", "katia3768", "qwerty123@mail.ru",1);
            addUser("nic", "nic3768", "qwerty120@mail.ru",2);

            addTheme("Animal");
            addTheme("Greeting");

            addTypeLesson("Reading");
            addTypeLesson("Vocabulary");
            addTypeLesson("Listening");
            addTypeLesson("Rules");

            addTest("What do you know about animals?");

            addQuestion("How many years have lions lived?");
            addAnswer("10 years","right",1 );
            addAnswer("5 years","wrong",1 );
            addAnswer("30 years","wrong",1 );
            addAnswer("50 years","wrong",1 );

            addLesson("lions live 10 years",1,1,1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabaseAndTables() throws SQLException{

            statement.executeUpdate("DROP SCHEMA IF EXISTS `denisova` ;");
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `denisova` DEFAULT CHARACTER SET utf8 ;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`roles` (\n" +
                    "  `id_role` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `role` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id_role`))\n" +
                    "ENGINE = InnoDB;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`users` (\n" +
                    "  `id_user` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `login` VARCHAR(45) NULL,\n" +
                    "  `password` VARCHAR(45) NULL,\n" +
                    "  `email` VARCHAR(45) NULL,\n" +
                    "  `id_role` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_user`),\n" +
                    "  INDEX `fk_users_roles_idx` (`id_role` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_users_roles`\n" +
                    "    FOREIGN KEY (`id_role`)\n" +
                    "    REFERENCES `denisova`.`roles` (`id_role`)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE RESTRICT)\n" +
                    "ENGINE = InnoDB;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`themes` (\n" +
                    "  `id_theme` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `theme` VARCHAR(150) NULL,\n" +
                    "  PRIMARY KEY (`id_theme`))\n" +
                    "ENGINE = InnoDB;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`tests` (\n" +
                    "  `id_test` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `test_name` VARCHAR(100) NULL,\n" +
                    "  PRIMARY KEY (`id_test`))\n" +
                    "ENGINE = InnoDB;");

             statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`questions` (\n" +
                    "  `id_question` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `question` VARCHAR(350) NULL,\n" +
                    "  `id_test` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_question`),\n" +
                    "  INDEX `fk_questions_tests1_idx` (`id_test` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_questions_tests1`\n" +
                    "    FOREIGN KEY (`id_test`)\n" +
                    "    REFERENCES `denisova`.`tests` (`id_test`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE)\n" +
                    "ENGINE = InnoDB;");

             statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`answers` (\n" +
                     "  `id_answer` INT NOT NULL AUTO_INCREMENT,\n" +
                     "  `answer` VARCHAR(350) NULL,\n" +
                     "  `status` VARCHAR(45) NULL,\n" +
                     "  `id_question` INT NOT NULL,\n" +
                     "  PRIMARY KEY (`id_answer`),\n" +
                     "  INDEX `fk_answers_questions1_idx` (`id_question` ASC) VISIBLE,\n" +
                     "  CONSTRAINT `fk_answers_questions1`\n" +
                     "    FOREIGN KEY (`id_question`)\n" +
                     "    REFERENCES `denisova`.`questions` (`id_question`)\n" +
                     "    ON DELETE NO ACTION\n" +
                     "    ON UPDATE NO ACTION)\n" +
                     "ENGINE = InnoDB;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`type_lesson` (\n" +
                    "  `id_type` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `type` VARCHAR(100) NULL,\n" +
                    "  PRIMARY KEY (`id_type`))\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`lessons` (\n" +
                    "  `id_lesson` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `theory` VARCHAR(150) NULL,\n" +
                    "  `id_test` INT NOT NULL,\n" +
                    "  `id_theme` INT NOT NULL,\n" +
                    "  `id_type` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_lesson`),\n" +
                    "  INDEX `fk_lessons_tests1_idx` (`id_test` ASC) VISIBLE,\n" +
                    "  INDEX `fk_lessons_themes1_idx` (`id_theme` ASC) VISIBLE,\n" +
                    "  INDEX `fk_lessons_type_lesson1_idx` (`id_type` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_lessons_tests1`\n" +
                    "    FOREIGN KEY (`id_test`)\n" +
                    "    REFERENCES `denisova`.`tests` (`id_test`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `fk_lessons_themes1`\n" +
                    "    FOREIGN KEY (`id_theme`)\n" +
                    "    REFERENCES `denisova`.`themes` (`id_theme`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `fk_lessons_type_lesson1`\n" +
                    "    FOREIGN KEY (`id_type`)\n" +
                    "    REFERENCES `denisova`.`type_lesson` (`id_type`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `denisova`.`users_lessons` (\n" +
                    "  `state` VARCHAR(45) NULL,\n" +
                    "  `id_user` INT NOT NULL,\n" +
                    "  `id_lesson` INT NOT NULL,\n" +
                    "  INDEX `fk_users_lessons_users1_idx` (`id_user` ASC) VISIBLE,\n" +
                    "  INDEX `fk_users_lessons_lessons1_idx` (`id_lesson` ASC) VISIBLE,\n" +
                    "  PRIMARY KEY (`id_user`, `id_lesson`),\n" +
                    "  CONSTRAINT `fk_users_lessons_users1`\n" +
                    "    FOREIGN KEY (`id_user`)\n" +
                    "    REFERENCES `denisova`.`users` (`id_user`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `fk_users_lessons_lessons1`\n" +
                    "    FOREIGN KEY (`id_lesson`)\n" +
                    "    REFERENCES `denisova`.`lessons` (`id_lesson`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
    }

    private static void addRole(String roleName) throws SQLException{
        statement.executeUpdate(
                "INSERT INTO `\"+config.getDataBaseName()+\"`.`roles` (`id_role`, `role`) " +
                        "VALUES (DEFAULT, '\"+roleName+\"');");
    }

    private static void addUser(String login, String password, String email, long role) throws SQLException{
        statement.executeUpdate(
                "INSERT INTO `\"+config.getDataBaseName()+\"`.`users` (`id_user`, `login`, `password`, `email`, `id_role`) " +
                        "VALUES (DEFAULT, '\"+ login+\"', '\"+password+\"', '\"+email+\"', '\"+ role+\"' );");
    }

    private static void addTheme(String theme) throws SQLException{
        statement.executeUpdate(
                "INSERT INTO `\"+config.getDataBaseName()+\"`.`themes` (`id_theme`, `theme`)" +
                        " VALUES (DEFAULT, '\"+ theme+\"');");
    }

    private static void addTypeLesson(String typeLesson) throws SQLException{
        statement.executeUpdate(
                "INSERT INTO `\"+config.getDataBaseName()+\"`.`type_lesson` (`id_type`, `type`) " +
                        "VALUES (DEFAULT, '\"+typeLesson+\"');");
    }

    private static void addTest(String testName) throws SQLException{
        statement.executeUpdate("INSERT INTO `\"+config.getDataBaseName()+\"`.`tests` (`id_test`, `test_name`)" +
                " VALUES (DEFAULT, '\"+testName+\"');");
    }

    private static void addQuestion(String question) throws SQLException{
        statement.executeUpdate("INSERT INTO `\"+config.getDataBaseName()+\"`.`questions` (`id_question`, `id_test`)" +
                "                VALUES (DEFAULT, '\"+question+\"');");
    }

    private  static void addAnswer(String answer, String status, long idQuestion) throws SQLException {
        statement.executeUpdate("INSERT INTO `\"+config.getDataBaseName()+\"`.`answers` (`id_answer`, `answer`, `status`, `id_question`)" +
                " VALUES (DEFAULT, '\"+answer+\"', '\"+status+\"', '\"+idQuestion+\"');");
    }

    private static void addLesson(String theory, long idTest, long idTheme, long idType) throws SQLException{
        statement.executeUpdate("INSERT INTO `\"+config.getDataBaseName()+\"`.`lessons` (`id_lesson`, `theory`, `id_test`, `id_theme`, `id_type`)" +
                " VALUES (DEFAULT, '\"+theory+\"', '\"+idTest+\"', '\"+idTheme+\"', '\"+idType+\"');");
    }
}
