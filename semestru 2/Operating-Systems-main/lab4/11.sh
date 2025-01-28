cat ps.fake | awk   '{NR > 1; sum += ; number +=1} END{print sum/number}'
