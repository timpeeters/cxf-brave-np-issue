# cxf-brave-np-issue

Reproduces a compatibility issue between recent versions of Spring Cloud (Greenwich.SR3+) and CXF 3.3.5.
CXF uses an older version of the Brave libraries and relies on a deprecated method from `brave-instrumentation-http`.
When calling this method, a `NullPointerException` is thrown that hides the original exception.

The test in this repository can be used to reproduce the problem.

https://github.com/apache/cxf/pull/639
