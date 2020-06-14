local lockKey = KEYS[1]
local lockValue = KEYS[2]
local lockTime = KEYS[3]
-- setnx info  result_1 返回 1 说明写入成功
local result_1 = redis.call('SETNX', lockKey, lockValue)
if result_1 == 1
    then
      local result_2 = redis.call('SETEX', lockKey,lockTime, lockValue)
      -- result_2 返回 OK
    return true
else
    return false
end