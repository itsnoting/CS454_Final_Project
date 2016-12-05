#!/usr/bin/python

# Rigoberto Moreno Delgado
# John Cox

import time
import Queue

state_table = []
visited = []
parent = []
symbol = []

def main():
        

        symbols = raw_input("Enter the symbols for the set (Example: 3,4,5)\n")
        symbol_lst = [int(n) for n in symbols.split(",")]
        x = raw_input("Enter a number for X\n")
        
        t0 = time.time()
        
        make_table(symbol_lst, int(x))
        breadth_first_search(symbol_lst, int(x))
        current = 0
        str_var = str(symbol[current])
        current = parent[current]
        while(current != 0):
                str_var += str(symbol[current])
                current = parent[current]
	str_var = str_var[::-1]
        print "Shortest length string accepted by the DFA is", str_var
        t1 = time.time()
        print t1-t0 


def make_table(symbol, x):
        for i in range(x):
                temp = []
                for s in symbol:
                        temp.append(delta(i, s, x))
                state_table.append(temp)

def delta(c_state, symbol, x):
        return (c_state * 10 + symbol) % x

def breadth_first_search(symbol_lst, x):
        for i in range(x):
                parent.append(-1)
                visited.append(False)
                symbol.append(-1)
                
        q = Queue.Queue()
        q.put(0)
        good = True
        while(good):
                out = q.get()
                for s in symbol_lst:
                        nxt = delta(out, s, x)
                        if not visited[nxt]:
                                visited[nxt] = True
                                parent[nxt] = out
                                symbol[nxt] = s
                                q.put(nxt)
                        if (nxt == 0):
                                good = False
                                break
                        
if __name__ == "__main__":
        main()
