#include <time.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <queue>
#include <chrono>
#include <ctime>

using namespace std;

void make_table(vector<int> v, int x);
int delta(int state, int symbol, int x);
void bfs(vector<int> v, int x);

vector<int*> state_table;
vector<int> parent;
vector<int> symbol;
vector<bool> visited; 

int main() {
  std::chrono::time_point<std::chrono::system_clock> start, end;
  string inputSet;
  int x;
  cout << "Enter the symbols for the set (Example: 3,4,5)" << endl;
  
  // Handle input set
  vector<int> v;
  
  getline(cin, inputSet);
  int temp;
  stringstream ss(inputSet);
  while (ss >> temp) {
      v.push_back(temp);
      if (ss.peek() == ',')
        ss.ignore();
   }
  // End input handling
  
  // Get number
  cout << "Enter a number for X\n" << endl;
  cin >> x;

  start = std::chrono::system_clock::now();
  make_table(v, x);
  bfs(v, x);
  int current = 0;
  string str_var = to_string(symbol[current]);
  current = parent[current];
  while (current != 0) {
    str_var += to_string(symbol[current]);
    current = parent[current];
  }
  cout << "Shortest length string accepted by the DFA is" << " ";
  for (int s = str_var.length(); s != -1; --s) {
    cout << str_var[s];
  }
  end = chrono::system_clock::now();
  chrono::duration<double> elapsed_seconds = end-start;
  time_t end_time = std::chrono::system_clock::to_time_t(end);
  cout << endl;
  cout << elapsed_seconds.count() << " seconds" << endl;

}

void make_table(vector<int> v, int x) {
  
  for (int i = 0; i < x; ++i) {
    int temp [v.size()];
    for (int j = 0; j < v.size(); ++j) {
      temp[j] = delta(i, v[j], x);
    }
    state_table.push_back(temp);
  }
}

int delta(int state, int symbol, int x) {
  return (state * 10 + symbol) % x;
}

void bfs(vector<int> symbol_lst,int x){
  for (int i = 0; i < x; ++i) {
    parent.push_back(-1);
    visited.push_back(false);
    symbol.push_back(-1);
  }
  queue<int> q;
  q.push(0);
  while (true) {
    int out = q.front();
    q.pop();
    for (int j = 0; j < symbol_lst.size(); ++j) {
      int next = delta(out, symbol_lst[j], x);
      if (!visited[next]) {
        visited[next] = true;
        parent[next] = out;
        symbol[next] = symbol_lst[j];
        q.push(next);
      }

      if (next == 0) {
         return;
      }
    } // End for loop
  } // End while loop
} // End bfs()

