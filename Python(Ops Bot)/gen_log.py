import random
from datetime import datetime, timedelta

def generate_dummy_logs(filename="server.log", num_lines=5000):
   
    log_templates = [
        ("INFO", "User clicked on homepage", 80),
        ("INFO", "Database connection healthy", 80),
        ("INFO", "Image assets loaded successfully", 80),
        ("WARNING", "Response time slightly elevated", 10),
        ("ERROR", "Database connection timeout", 3),
        ("CRITICAL", "Payment gateway unreachable", 1),
        ("FAILED LOGIN", "Invalid password attempt for user: admin", 2),
        ("FAILED LOGIN", "Multiple failed attempts from IP: 192.168.1.104", 2)
    ]

    weighted_choices = []
    for level, msg, weight in log_templates:
        weighted_choices.extend([(level, msg)] * weight)

    
    current_time = datetime.now() - timedelta(days=1)

    try:
        with open(filename, "w") as file:
            for _ in range(num_lines):
                current_time += timedelta(seconds=random.randint(1, 20))
                
                level, message = random.choice(weighted_choices)
                time_str = current_time.strftime("%Y-%m-%d %H:%M:%S")
                log_line = f"[{time_str}] [{level}] {message}\n"
                
                file.write(log_line)
                
        print("Success!")
        
    except IOError as e:
        print(f"File writing failed: {e}")

if __name__ == "__main__":
    generate_dummy_logs()