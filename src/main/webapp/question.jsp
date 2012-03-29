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
						<tr>
							<td class="chooseAnswer"><input type="radio" value="1"
								name="answer">A</input></td>
							<td class="answertext">${answer1}</td>
						</tr>

						<tr>
							<td class="chooseAnswer"><input type="radio" value="2"
								name="answer">B</input></td>
							<td class="answertext">${answer2}</td>
						</tr>

						<tr>
							<td class="chooseAnswer"><input type="radio" value="3"
								name="answer">C</input></td>
							<td class="answertext">${answer3}</td>
						</tr>

						<tr>
							<td class="chooseAnswer"><input type="radio" value="4"
								name="answer">D</input></td>
							<td class="answertext">${answer4}</td>
						</tr>
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