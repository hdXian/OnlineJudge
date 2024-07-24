def solution(id_list, report, k):
    report_stack = {} # 각 사용자가 신고받은 횟수
    report_users = {} # 각 사용자가 신고한 유저들
    blocked_users = [] # 차단된 유저들
    email_stack = {} # 각 사용자가 받는 이메일 갯수
    
    for name in id_list:
        report_stack[name] = 0
        email_stack[name] = 0
        report_users[name] = set()
    
    for r in report:
        user, reported_user = map(str, r.split())
        report_users[user].add(reported_user)
    
    for users in report_users:
        for user in report_users[users]:
            report_stack[user] += 1
    
    for user in report_stack:
        if report_stack[user] >= k:
            blocked_users.append(user)
    
    for user in blocked_users:
        for report_user in report_users:
            if user in report_users[report_user]:
                email_stack[report_user] += 1
    
    answer = [email_stack[user] for user in email_stack]
    return answer