<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="../../../../ARenaWA.css">
</head>
<body>
	<div class="questionBody">
		<c:if test="${question != null}">
			<div class="question">
				<h1>${question }</h1>
			</div>

			<div class="answer">
				<form method="post">
					<table>
						<c:if test="${answer1 != null}">
							<tr>
								<td class="chooseAnswer"><input type="radio" value="1"
									name="answer">A</input></td>
								<td class="answertext">${answer1}</td>
							</tr>
						</c:if>

						<c:if test="${answer2 != null}">
							<tr>
								<td class="chooseAnswer"><input type="radio" value="2"
									name="answer">B</input></td>
								<td class="answertext">${answer2}</td>
							</tr>
						</c:if>

						<c:if test="${answer3 != null}">
							<tr>
								<td class="chooseAnswer"><input type="radio" value="3"
									name="answer">C</input></td>
								<td class="answertext">${answer3}</td>
							</tr>
						</c:if>

						<c:if test="${answer4 != null}">
							<tr>
								<td class="chooseAnswer"><input type="radio" value="4"
									name="answer">D</input></td>
								<td class="answertext">${answer4}</td>
							</tr>
						</c:if>
						
						<c:if test="${answer == 'true'}">
							<tr>
								<td>
									<textarea rows="2" cols="40" name="answer"></textarea>
								</td>
							</tr>
						</c:if>
					</table>
					<br /> <input type="hidden" value="${questionId }"
						name="questionId" /> <input type="submit" value="Versturen"
						name="send" />
				</form>
			</div>
		</c:if>
		<c:if test="${question == null}">
		Question not found!
	</c:if>
	</div>
</body>
</html>