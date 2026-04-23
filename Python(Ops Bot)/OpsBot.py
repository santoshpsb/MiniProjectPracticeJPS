import re
import os
from datetime import datetime

def scan_server_logs(log_file="server.log"):
    

    try:
        print(f" BEFORE VIEW: {log_file} ( 100 Lines) ")
        with open(log_file, "r", encoding="utf-8") as file:
            for _ in range(100):
                line = file.readline()
                if not line: 
                    break 
                print(line.strip())
        print("\n")
        
        threat_pattern = re.compile(r"ERROR|CRITICAL|FAILED LOGIN",re.IGNORECASE)
        flagged_logs = []
        threat_counts = {
            "ERROR": 0,
            "CRITICAL": 0,
            "FAILED LOGIN": 0
        }

        with open(log_file, "r", encoding="utf-8") as file:
            for line in file:
                match = threat_pattern.search(line)
                if match:
                    flagged_logs.append(line.strip())
                    error_type = match.group()
                    threat_counts[error_type] += 1
                    
        
        generate_security_report(flagged_logs, threat_counts)

    except FileNotFoundError:
        print(f"Error: '{log_file}' was not found.")

def generate_security_report(flagged_logs, threat_counts):
    today_str = datetime.now().strftime("%Y-%m-%d")
    report_filename = f"security_alert_{today_str}.txt"


    try:
        with open(report_filename, "w", encoding="utf-8") as report_file:
            
            
            report_file.write(" THREAT ANALYTICS \n")
            for threat, count in threat_counts.items():
                report_file.write(f"{threat}: {count} occurrences\n")
            
            report_file.write("\n CRITICAL LOGS \n")
            for log in flagged_logs:
                report_file.write(f"{log}\n")

        file_size_bytes = os.path.getsize(report_filename)
        file_size_kb = round(file_size_bytes / 1024, 2)
        
       
        print(f"File: {report_filename}")
        print(f"Size: {file_size_kb} KB \n")
        print(f"PREVIEW: {report_filename} (20 Lines)\n")
        with open(report_filename, "r", encoding="utf-8") as report_file:
            for _ in range(20):
                line = report_file.readline()
                if not line: 
                    break
                print(line.strip())

    except IOError as e:
        print(f"Error: Failed to write to file. {e}")

if __name__ == "__main__":
    scan_server_logs()