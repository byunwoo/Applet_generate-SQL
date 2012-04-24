import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MakeQuery extends JFrame {
    private static final long serialVersionUID = -2254999084507112395L;

    JTextField jTextField;
    ButtonGroup group;
    JTextArea ta;
    String buttonText;

    public enum JobType {
        MERGE, INSERT, UPDATE, DELETE;
    }

    static HashMap<String, JobType> hmType = new HashMap<String, JobType>();
    static HashMap<String, String> hmParam = new HashMap<String, String>();

    public MakeQuery() {

        hmType.put("MERGE", JobType.MERGE);
        hmType.put("INSERT", JobType.INSERT);
        hmType.put("UPDATE", JobType.UPDATE);
        hmType.put("DELETE", JobType.DELETE);

        // 필드 자동 변환
        hmParam.put("reg_i", "#@usid#");
        hmParam.put("reg_dt", "SYSTIMESTAMP");
        hmParam.put("mod_id", "#@usid#");
        hmParam.put("mod_dt", "SYSTIMESTAMP");
        hmParam.put("hspt_dvcd", "#@hspt_dvcd#");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container p = getContentPane();
        p.setLayout(new FlowLayout());

        jTextField = new JTextField(20);
        p.add(jTextField);

        JButton jButton = new JButton("확인");
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jTextField.getText().trim().length() < 1 || buttonText == null || buttonText.length() < 1) {

                } else {
                    ta.setText(process(jTextField.getText(), hmType.get(buttonText.toUpperCase())));
                }
            }
        });
        p.add(jButton);

        group = new ButtonGroup();
        String[] sa = {"Insert", "Update", "Delete", "Merge"};

        for (int i = 0; i < sa.length; ++i) {
            JRadioButton b = new JRadioButton(sa[i]);
            group.add(b);
            p.add(b);
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonText = e.getActionCommand();
                }
            });
        }

        ta = new JTextArea(20, 40);
        JScrollPane jsp = new JScrollPane(ta);
        p.add(jsp);

        setSize(500, 500);
    }

    public String process(String schma, JobType gubun) {

        StringBuffer resultQuery = new StringBuffer();
        int index = schma.indexOf('.');

        String owner = schma.substring(0, index).toUpperCase();
        String tableName = schma.substring(index + 1).toUpperCase();

        StringBuffer query = new StringBuffer();
        query.append("select t.table_name, \n");
        query.append("      tc.comments, \n");
        query.append("      c.column_id, \n");
        query.append("      cc.comments as column_comments, \n");
        query.append("      c.column_name, \n");
        query.append("      c.data_type|| \n");
        query.append("      case when c.data_type = 'NUMBER' then  \n");
        query.append("        case when c.data_scale = 0 then \n");
        query.append("             '('||to_char(c.data_precision)||')'  \n");
        query.append("        else '('||to_char(c.data_precision)||','||to_char(c.data_scale)||')'  \n");
        query.append("        end  \n");
        query.append("      else  \n");
        query.append("        case when c.data_type in('CHAR','VARCHAR2') then  \n");
        query.append("             '('||to_char(c.data_length)||')'  \n");
        query.append("        end  \n");
        query.append("      end \"DATA_TYPE\",  \n");
        query.append("      decode(c.nullable,'N',  \n");
        query.append("             decode(  \n");
        query.append("                    (  \n");
        query.append("                    select 'PK' from all_constraints uc,  \n");
        query.append("                                     all_cons_columns ucc  \n");
        query.append("                               where (t.owner = uc.owner and t.table_name = uc.table_name and uc.constraint_type = 'P')  \n");
        query.append("                                 and (t.owner = ucc.owner and uc.constraint_name = ucc.constraint_name and uc.table_name = ucc.table_name and  \n");
        query.append("                                      c.column_name = ucc.column_name)  \n");
        query.append("                    ) \n");
        query.append("                    ,'PK','Y'  -- Not Null 대신 PK로 표기  \n");
        query.append("                   )  \n");
        query.append("            ) PK,  \n");
        query.append("       '' \"FK\",  \n");
        query.append("       decode(c.nullable,'N','NN') \"Null\",  \n");
        query.append("       c.data_default  \n");
        query.append(" from all_tables t,  \n");
        query.append("      all_tab_comments tc,  \n");
        query.append("      all_tab_columns c,  \n");
        query.append("      all_col_comments cc  \n");
        query.append("where t.owner = '" + owner + "' and t.table_name = '" + tableName + "' -- 업무별변경부분임  \n");
        query.append("and t.owner = tc.owner and t.table_name = tc.table_name  \n");
        query.append("and t.owner = c.owner and t.table_name = c.table_name  \n");
        query.append("and c.owner = cc.owner and c.table_name = cc.table_name and c.column_name = cc.column_name  \n");
        query.append("order by  \n");
        query.append("t.table_name,  \n");
        query.append("c.column_id ");
        try {
            DriverManager.registerDriver((Driver) Class.forName("oracle.jdbc.OracleDriver").newInstance());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@220.125.154.207:21521:CDC", "hyd", "hyddnsdud123");
            PreparedStatement pStmt = conn.prepareStatement(query.toString());

            pStmt.setQueryTimeout(200);
            System.out.println(" ========================" + pStmt.getQueryTimeout());

            ResultSet rs = pStmt.executeQuery();

            StringBuffer firstSb = new StringBuffer();
            StringBuffer secondSb = new StringBuffer();
            StringBuffer thirdSb = new StringBuffer();
            StringBuffer lastSb = new StringBuffer();
            StringBuffer fourth = new StringBuffer();
            while (rs.next()) {
                String columnName = rs.getString("column_name").toLowerCase();
                String paramter = getParameter(columnName, rs.getString("data_type"));
                String columnComments = rs.getString("column_comments");
                // --").append(columnComments).append("\n");
                if (firstSb.length() < 1) {
                    // 헤더
                    switch (gubun) {
                    case MERGE:
                        firstSb.append("MERGE INTO ").append(schma.toLowerCase()).append(" a \n");
                        firstSb.append("USING (SELECT ");
                        secondSb.append(" WHEN NOT MATCHED THEN").append("\n INSERT  \n ( \n");
                        thirdSb.append(" VALUES ( \n");
                        fourth.append(" WHEN MATCHED THEN").append("\n UPDATE  \n SET \n");

                        break;
                    case INSERT:
                        firstSb.append("INSERT INTO ").append(schma.toLowerCase()).append("\n");
                        firstSb.append("( ");
                        secondSb.append(" VALUES (");
                        break;
                    case UPDATE:
                        firstSb.append(" UPDATE ").append(schma.toLowerCase()).append("\n");
                        firstSb.append(" SET  ");
                        break;
                    case DELETE:
                        firstSb.append("DELETE FROM ").append(schma.toLowerCase()).append("\n");
                        break;
                    default:
                        break;
                    }
                }
                // 컬럼
                switch (gubun) {
                case MERGE:
                    firstSb.append(paramter).append(" AS ").append(columnName).append(", -- ").append(columnComments).append("\n");
                    secondSb.append(columnName).append(", -- ").append(columnComments).append("\n");
                    thirdSb.append("b.").append(columnName).append(", -- ").append(columnComments).append("\n");
                    if (!"Y".equals(rs.getString("pk"))) {
                        fourth.append(columnName).append("  =  NVL(b.").append(columnName).append(", ").append(columnName);
                        fourth.append("), -- ").append(columnComments).append("\n");
                    } // end if
                    break;
                case INSERT:
                    firstSb.append(columnName).append(", -- ").append(columnComments).append("\n");
                    secondSb.append(paramter).append(", -- ").append(columnComments).append("\n");
                    break;
                case UPDATE:
                    if (!"Y".equals(rs.getString("pk"))) {
                        firstSb.append(columnName).append("  =  ").append(paramter).append(", -- ").append(columnComments).append("\n");
                    } // end if
                    break;
                case DELETE:
                    break;
                default:
                    break;
                }

                if ("Y".equals(rs.getString("pk"))) {
                    if (lastSb.length() > 0) {
                        lastSb.append(" AND ");
                    } // end if
                    if (gubun == JobType.MERGE) {
                        lastSb.append(" a.").append(columnName).append(" =  b.").append(columnName);
                    } else {
                        lastSb.append(columnName).append(" = ").append(paramter);
                    }
                    lastSb.append("\n");

                }
            }
            if (firstSb.length() > 0) {
                switch (gubun) {
                case MERGE:
                    resultQuery.append(replaceLastComma(firstSb.toString(), "")).append(" FROM dual) b \n ON (");
                    resultQuery.append(lastSb).append(") \n");
                    resultQuery.append(replaceLastComma(secondSb.toString(), ")"));
                    resultQuery.append(replaceLastComma(thirdSb.toString(), ")"));
                    resultQuery.append(replaceLastComma(fourth.toString(), ""));
                    break;
                case INSERT:
                    resultQuery.append(replaceLastComma(firstSb.toString(), ")"));
                    resultQuery.append(replaceLastComma(secondSb.toString(), ")"));
                    break;
                case UPDATE:
                    resultQuery.append(replaceLastComma(firstSb.toString(), "")).append(" WHERE ");
                    resultQuery.append(lastSb);
                    break;
                case DELETE:
                    resultQuery.append(firstSb).append(" WHERE ");
                    resultQuery.append(lastSb);
                    break;
                default:
                    break;
                }
            }
            rs.close();
            pStmt.close();
            conn.close();
            resultQuery.append(";");
            // System.out.println(resultQuery.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultQuery.toString();

    }

    public String getParameter(String columnName, String type) {
        String value;
        if (hmParam.containsKey(columnName)) {
            value = hmParam.get(columnName);
        } else {
            if ("DATE".equalsIgnoreCase(type)) {
                value = "#" + columnName + "::DATE@yyyyMMdd#";
            } else if ("TIMESTAMP".equalsIgnoreCase(type)) {
                value = "#" + columnName + "::TIMESTAMP@yyyyMMddHHmmss#";
            } else {
                value = "#" + columnName + "#";
            }
        }

        return value;
    }

    public String replaceLastComma(String source, String replace) {
        int index = source.lastIndexOf(',');
        StringBuffer tmp = new StringBuffer(source);
        tmp.replace(index, index + 1, replace);

        return tmp.toString();
    }

    public static void main(String[] args) {
        MakeQuery make = new MakeQuery();
        make.setVisible(true);
    }

}
