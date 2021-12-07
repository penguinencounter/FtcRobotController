import requests
import json
import os
import re

ROBOT_HTTP_ADDR = "http://192.168.43.1:8080/"
CODE_DIR = "./TeamCode/src/main/java/org/firstinspires/ftc/teamcode"

FILE_LIST_PATH = "java/file/tree"

def get_files_on_server(robot_url, list_path):
    full_url = robot_url + list_path
    download = requests.get(full_url)
    return json.loads(download.text)['src']


def get_files_to_remove(code_dir_path: str, remote_src: list):
    files = os.listdir(code_dir_path)
    remote_pathfix = ""
    for path in remote_src:
        match = re.match(r'(.*\/).*\.java', path)
        if match is None:
            continue
        remote_pathfix = match.group(1)
    need_to_remove = []
    for file in files:
        if remote_pathfix+file in remote_src:
            need_to_remove.append(remote_pathfix+file)
    return need_to_remove


if __name__ == "__main__":
    files = get_files_on_server(ROBOT_HTTP_ADDR, FILE_LIST_PATH)
    print(files)
    remove = get_files_to_remove(CODE_DIR, files)
    print(remove)
