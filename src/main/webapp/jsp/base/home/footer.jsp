<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/static_resources/js/jquery3.1.1.min.js"><\/script>')</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.3/js/tether.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>

<!-- csrf part -->
<script type="text/javascript">
  var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
  var csrfHeader = $("meta[name='_csrf_header']").attr("content");
  var csrfToken = $("meta[name='_csrf']").attr("content");
</script>

<script type="text/javascript" src="<c:url value='/static_resources/js/home/homeV4.js'/>"></script>
<!-- Bootstrap core JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>

<footer>
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
        <ul class="list-inline text-center">
        <!--
          <li class="list-inline-item">
            <a href="#">
              <span class="fa-stack fa-lg">
                <i class="fas fa-circle fa-stack-2x"></i>
                <i class="fab fa-twitter fa-stack-1x fa-inverse"></i>
              </span>
            </a>
          </li>
          <li class="list-inline-item">
            <a href="#">
              <span class="fa-stack fa-lg">
                <i class="fas fa-circle fa-stack-2x"></i>
                <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
              </span>
            </a>
          </li>
          <li class="list-inline-item">
            <a href="#">
              <span class="fa-stack fa-lg">
                <i class="fas fa-circle fa-stack-2x"></i>
                <i class="fab fa-github fa-stack-1x fa-inverse"></i>
              </span>
            </a>
          </li>
         -->

        </ul>
        <PRE><p class="copyright text-muted" style="text-align: center; vertical-align: middle;" id="footMarker" visitcount="${visitCount}"></p></PRE>
      </div>
    </div>
  </div>
</footer>
