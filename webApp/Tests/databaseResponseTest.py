# Database/Server Tests

# URL requests are made to the server and the response is measured against the expected reply.

# If the tests pass or fail, the output will be in the console.

import urllib

def testUrl(url,expectedResponse):
	serverResponse = ""
	response = urllib.urlopen(url)
	for line in response:
		serverResponse = serverResponse + line.rstrip()
	if (serverResponse == expectedResponse):
		return True
	else:
		print serverResponse
		print expectedResponse
		return False


def testAll():

	url = 'http://192.168.0.22:8080/trainer/data'
	expectedResponse = """[{"id":101,"name":"Andrew Lumsden","address":"123 sandwich street","email":"111@leeds.ac.uk","phone":"111"},{"id":102,"name":"Edward Cottle","address":"321 curry grove","email":"222@leeds.ac.uk","phone":"222"},{"id":103,"name":"Samrudh Sharma","address":"45 Italian food lane","email":"333@leeds.ac.uk","phone":"333"},{"id":104,"name":"Michael Glad","address":"34 Fish and Chips street","email":"444@leeds.ac.uk","phone":"444"},{"id":105,"name":"Callum Boustaed","address":"67 KFC avenue","email":"555@leeds.ac.uk","phone":"555"}]"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'

	url = 'http://192.168.0.22:8080/existingcourse/data'
	expectedResponse = """[{"id":401,"courseId":120,"classroomId":2,"trainerId":101,"startTime":1494068400000,"endTime":1495008000000},{"id":402,"courseId":121,"classroomId":3,"trainerId":102,"startTime":1493982000000,"endTime":1494748800000},{"id":403,"courseId":122,"classroomId":4,"trainerId":103,"startTime":1494154800000,"endTime":1495008000000},{"id":404,"courseId":123,"classroomId":5,"trainerId":104,"startTime":1494241200000,"endTime":1495094400000},{"id":405,"courseId":125,"classroomId":6,"trainerId":104,"startTime":1494241200000,"endTime":1495094400000}]"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'

	url = 'http://192.168.0.22:8080/rqstdcourse/?ID=120'
	expectedResponse = """{"id":120,"title":"Software Engineering","description":"Understand the software life-cycle. Select a development process appropriate for a given task and context. Use appropriate tools to manage the development process. Work effectively as team to capture the requirements, produce a design and complete the implementation for a given task.\\n","capacity":50,"duration":15,"prerequisiteId1":0,"prerequisiteId2":0,"prerequisiteId3":0}"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'

	url = 'http://192.168.0.22:8080/existingbystring/?SEARCHTERM=comput'
	expectedResponse = """[{"courseId":125,"existingCourseId":405,"classroomId":6,"title":"Algorithms","description":"n completion of this module, students should understand and be proficient in analysis and design of key algorithms that are fundamental to computing (searching, sorting, number conversion, goal seeking, backtracking) and in application of basic data structures (arrays, lists, stacks, and priority queues).","startTimeString":"2017-05-08 12:00","endTimeString":"2017-05-18 09:00"}]"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'

	url = 'http://192.168.0.22:8080/usercourses/?ID=101'

	expectedResponse = """[{"id":184,"username":101,"existingid":401,"course":120,"finished":false,"startTime":1494068400000,"endTime":1495008000000},{"id":185,"username":101,"existingid":402,"course":121,"finished":false,"startTime":1493982000000,"endTime":1494748800000},{"id":186,"username":101,"existingid":403,"course":122,"finished":false,"startTime":1494154800000,"endTime":1495008000000},{"id":287,"username":101,"existingid":405,"course":125,"finished":false,"startTime":1494241200000,"endTime":1495094400000},{"id":288,"username":101,"existingid":404,"course":123,"finished":false,"startTime":1494241200000,"endTime":1495094400000}]"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'

	url = 'http://192.168.0.22:8080/classroomById/?CLASSROOMID=2'

	expectedResponse = """{"id":2,"name":"Rodger Stevens LT 19","city":"Leeds","address":"LS2 9NH Roger Stevens Building","capacity":100,"type":"Lecture theatre","projector":false,"studentComp":false,"whiteboard":true,"audioRecording":true,"videoRecording":false,"wheelchairAccess":true,"listeningSystem":false}"""

	if (testUrl(url, expectedResponse) == True):
		print 'Test at ' + url +' Passed'
	
	else:
		print 'Test at ' + url +' Failed'



testAll()
	


 
