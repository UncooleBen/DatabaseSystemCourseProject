import argparse
import os
import shutil
import io
import random

def clean():
    print('Log: Cleaning files for deployment ...')
    clean_filelist = ['./tomcat-docker-startup/MeetHere.war', './mysql-docker-startup/meethere.sql']
    for filename in clean_filelist:
        print('Log: Deleting file `{}` ...'.format(filename))
        if os.path.exists(filename):
            os.remove(filename)
            print('Log: SUCCESS')
        else:
            print('Warning: SKIP')
    clean_dirlist = ['./tomcat-docker-startup', './mysql-docker-startup']
    for dirname in clean_dirlist:
        print('Log: Deleting directory `{}` ...'.format(dirname))
        if os.path.exists(dirname):
            shutil.rmtree(dirname)
            print('Log: SUCCESS')
        else:
            print('Warning: SKIP')
    print('Log: Successfully cleaned deployment files.')

def config(db):
    if (db == 'sample'):
        config_sample()
    elif (db == 'production'):
        config_production()
    else:
        print('Error: Unknown database `{}`. Please choose between `sample` and `production`.'.format(db))

def mkdir():
    make_dirlist = ['./tomcat-docker-startup', './mysql-docker-startup']
    for dirname in make_dirlist:
        print('Log: Making directory `{}` ...'.format(dirname))
        if not (os.path.exists(dirname)):
            os.mkdir(dirname)
            print('Log: SUCCESS')
        else:
            print('Warning: SKIP')
    print('Log: Making directory succeeded.')

def copy_war():
    print('Log: Copying war file ...')
    if not (os.path.exists('./tomcat-docker-startup/MeetHere.war')):
        shutil.copy('./target/MeetHere.war', './tomcat-docker-startup/MeetHere.war')
        if (os.path.exists('./tomcat-docker-startup/MeetHere.war')):
            print('Log: SUCCESS')
        else:
            print('Error: Copy war file failed.')
            pass
    else:
        print('Warning: SKIP')
    print('Log: Copying war file succeeded.')



def config_sample():
    mkdir()
    copy_war()
    common = io.open('./sql/declaration.sql', mode='r', encoding='utf-8')
    sample = io.open('./sql/sample.sql', mode='r', encoding='utf-8')
    output = io.open('./mysql-docker-startup/meethere.sql', mode='a', encoding='utf-8')
    print('Log: Writing SQL script for sample database ...')
    output.write(common.read())
    output.write(sample.read())
    common.close()
    sample.close()
    if (os.path.exists('./mysql-docker-startup/meethere.sql')):
        output.close()
        print('Log: Writing SQL script for `sample` database succeeded.')
    else:
        print('Error: Failed to create SQL script for sample database. Aborting ...')
        pass


def config_production():
    mkdir()
    copy_war()
    common = io.open('./sql/declaration.sql', mode='r', encoding='utf-8')
    sample = io.open('./sql/sample.sql', mode='r', encoding='utf-8')
    output = io.open('./mysql-docker-startup/meethere.sql', mode='a', encoding='utf-8')
    print('Log: Writing SQL script for production database ...')
    output.write(common.read())
    output.write(sample.read())
    common.close()
    sample.close()
    output.write('START TRANSACTION;\n')
    # Create user
    print('Log: Creating tuples for `t_user` ...')
    user_insert = 'INSERT INTO `t_user` VALUES ({}, {}, {}, {}, {}, {}, {});\n'
    for i in range(3, 101):
        output.write(user_insert.format(i, '\'{}\''.format(i), '\'{}\''.format(i), '\'{}\''.format(i), '\'FEMALE\'', 1, '\'{}\''.format(i)))
        print('{}\tof 100\r'.format(i), end='')
    print('100\tof 100')
    print('Log: Creating tuples for `t_user` succeeded.')
    # Create comment
    print('Log: Creating tuples for `t_comment` ...')
    comment_insert = 'INSERT INTO `t_comment` (`user_id`, `building_id`, `date`, `content`, `verified`) VALUES ({}, {}, {}, {}, {});\n'
    contents = ['\'好评\'','\'中评\'','\'差评\'']
    for i in range(0, 5000):
        output.write(comment_insert.format(random.randint(2,100), random.randint(1,4), 'TIMESTAMP(\'2020-06-01\')', contents[random.randint(0,2)], 1))
        print('{}\tof 5000\r'.format(i+1), end='')
    print('5000\tof 5000')
    print('Log: Creating tuples for `t_comment` succeeded.')
    # Create record
    print('Log: Creating tuples for `t_record` ...')
    record_insert = 'INSERT INTO `t_record` (`time`, `start_date`, `end_date`, `user_id`, `building_id`, `verified`) VALUES ({}, {}, {}, {}, {}, {});\n'
    for i in range(0, 5000):
        output.write(record_insert.format('TIMESTAMP(\'2020-06-01\')', 'TIMESTAMP(\'2020-06-02\')', 'TIMESTAMP(\'2020-06-03\')', random.randint(2,100), random.randint(1,4), random.randint(0,1)))
        print('{}\tof 5000\r'.format(i+1), end='')
    print('5000\tof 5000')
    print('Log: Creating tuples for `t_record` succeeded.')
    if (os.path.exists('./mysql-docker-startup/meethere.sql')):
        output.write('COMMIT;\n')
        output.close()
        print('Log: Writing SQL script for `production` database succeeded.')
    else:
        print('Error: Failed to create SQL script for sample database. Aborting ...')
        pass

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Configures the database final webapp project.')
    group = parser.add_mutually_exclusive_group()
    group.add_argument('-clean', action='store_true')
    group.add_argument('-config', action='store_true')
    parser.add_argument('-db', action='store', help='Choose between `sample` and `production` to specify the database to generate.')
    args = parser.parse_args()
    if (args.clean):
        clean()
    elif (args.config):
        config(args.db)