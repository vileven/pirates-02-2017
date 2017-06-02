package api.controllers;


import api.model.Score;
import api.services.AccountService;
import api.controllers.generic.ApplicationController;
import api.utils.info.SelectParametersInfo;
import api.utils.info.ValueInfo;
import api.utils.pair.Pair;
import api.utils.response.Response;
import api.utils.response.SelectBody;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static api.controllers.SessionController.USER_ID;


@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/scores")
public class GameController extends ApplicationController {

    private final JdbcTemplate template;

    @Autowired
    public GameController(AccountService accountService, ApplicationContext appContext, JdbcTemplate template) {
        super(accountService, appContext);
        this.template = template;
    }

    /*
        ToDo: Add real scores, not the fake ones
        ToDo: Replace POST with GET (also MAKE SURE to fix frontend)
     */
    @PostMapping("")
    public ResponseEntity<?> scores(HttpSession session) {

        final String[] names = { "lol", "kek", "cheburek", "Valentin", "VaNilKA", "top", "kokos",
                                 "Pachome", "Sobaka", "Gennadiy", "Alconafter", "EvaPWNZ", "BanePWNZ"};

        final HashMap<String, Integer> scores = new HashMap<>();
        for (String name: names) {
            scores.put(name, ThreadLocalRandom.current().nextInt(0, 2000));
        }

        final Map<String, Integer> sorted = scores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        final List<Object> list = new ArrayList<>();

        for (String key: sorted.keySet()) {
            list.add(new Object() {
                public final String login = key;
                public final int score = sorted.get(key);
            });
        }

        return ResponseEntity.ok(list);
    }



    @PostMapping("/create")
    public ResponseEntity<?> createScore(@RequestBody ValueInfo info, HttpSession session) {
        final Long userId = (Long) session.getAttribute(USER_ID);
        if (userId == null) {
            return Response.invalidSession();
        }

        template.update("INSERT INTO scores (user_id, score) VALUES (?, ?) " +
                "ON CONFLICT (user_id) DO " +
                "UPDATE SET score = ? ;", userId, info.getValue(), info.getValue());

        return Response.ok("success");
    }

    @GetMapping("/select")
    public ResponseEntity<?> getScores(@RequestBody SelectParametersInfo info) {
        return ResponseEntity.ok(new SelectBody(selectWithParams(info.getLimit(), info.getOffset(),
                info.getOrders(), info.getFilters()),getCount(info.getFilters())));
    }

    public List<Score> selectWithParams(Integer limit, Integer offset, @Nullable List<Pair<String, String>> orders,
                                        @Nullable List<Pair<String, String>> filters) {

        final StringBuilder sqlConstructor = new StringBuilder();
        sqlConstructor.append(" SELECT u.login as userlogin, s.score ")
                .append("FROM scores s JOIN users u ON s.user_id = u.id ");

        if (filters != null && !filters.isEmpty()) {
            sqlConstructor.append(" WHERE ");
            for (int i = 0; i < filters.size(); i++) {

                if (filters.get(i).getKey().equals("score")) {
                    sqlConstructor
                            .append("s.")
                            .append(filters.get(i).getKey())
                            .append(" = ")
                            .append(filters.get(i).getValue())
                    ;
                } else {
                    sqlConstructor
                            .append("u.")
                            .append(filters.get(i).getKey())
                            .append(" ~* '")
                            .append(filters.get(i).getValue())
                            .append('\'')
                    ;
                }

                if (i != filters.size() - 1) {
                    sqlConstructor.append(" AND ");
                }
            }
        }

        if (orders != null && !orders.isEmpty()) {
            sqlConstructor.append("ORDER BY ");

            for (int i = 0; i < orders.size(); i++) {
                sqlConstructor
                        .append(orders.get(i).getKey())
                        .append(' ')
                        .append(orders.get(i).getValue())
                ;

                if (i != orders.size() - 1) {
                    sqlConstructor.append(", ");
                }
            }
        }
        sqlConstructor.append(" LIMIT ? OFFSET ?");

        return template.query(sqlConstructor.toString(),
                (rs, rowNum) -> new Score(rs.getString("userlogin"), rs.getLong("score")),
                limit, offset);
    }

    public Integer getCount(List<Pair<String, String>> filters) {
        final StringBuilder sqlBuilder = new StringBuilder().append("SELECT count(*) ")
                .append("FROM scores s JOIN users u ON s.user_id = u.id ");

        if (filters != null && !filters.isEmpty()) {
            sqlBuilder.append(" WHERE  ");
            for (int i = 0; i < filters.size(); i++) {
                if (filters.get(i).getKey().equals("score")) {
                    sqlBuilder
                            .append("s.")
                            .append(filters.get(i).getKey())
                            .append(" = ")
                            .append(filters.get(i).getValue())
                    ;
                } else {
                    sqlBuilder
                            .append(" u.")
                            .append(filters.get(i).getKey())
                            .append(" ~* '")
                            .append(filters.get(i).getValue())
                            .append('\'')
                    ;
                }

                if (i != filters.size() - 1) {
                    sqlBuilder.append(" AND ");
                }
            }
        }

        return template.queryForObject(sqlBuilder.toString(), Integer.class);
    }


}
