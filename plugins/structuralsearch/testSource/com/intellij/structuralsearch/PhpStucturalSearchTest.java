package com.intellij.structuralsearch;

import com.jetbrains.php.lang.PhpFileType;

/**
 * @author Eugene.Kudelevsky
 */
public class PhpStucturalSearchTest extends StructuralSearchTestCase {
  public void test1() throws Exception {
    String s = "<?php\n" +
               "if ($exp) {\n" +
               "  print b;\n" +
               "}";
    doTest(s, "if ($exp$) {\n" +
              "  $st$\n" +
              "}", 1);
    doTest(s, "if ($exp$) {\n" +
              "  $st$;\n" +
              "}", 1);
  }

  public void test2() throws Exception {
    String s = "<?php\n" +
               "$this->_buckets = $bu;";
    // todo: support $ prefix
    /*doTest(s, "$a$->_buckets = $bu;", 1);
    doTest(s, "$a$->_buckets = $c$;", 1);
    doTest(s, "$a$->$b$ = $c$;", 1);
    doTest(s, "$a$->_buckets = $bu1;", 0);*/

    doTest(s, "$this->_buckets = $bu;", 1);
    doTest(s, "$this->_buckets = $c$;", 1);
    doTest(s, "$this->$b$ = $c$;", 1);
    doTest(s, "$this->_buckets = $bu1;", 0);
  }

  private void doTest(String source,
                      String pattern,
                      int expectedOccurences) {
    assertEquals(expectedOccurences,
                 findMatches(source, pattern, true, PhpFileType.INSTANCE, null, PhpFileType.INSTANCE, null, false)
                   .size());
  }
}
