import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListDriver{

  private static int max_no_threads = 12;
	private static int num_threads = -1;
	private static ThreadDemo[] thread_pool = new ThreadDemo[max_no_threads];

  private static ConcurrentList fine_list;
  private static ConcurrentList lazy_list;
  private static ConcurrentList coarse_list;
  
  public static int list_type = -1;//0-coarse, 1-fine, 2-lazy
  public static int op_type = -1;//0-high add/low rem, 1- high add/low contain, 2- even add/contain 

  private static void init_lists(){
      fine_list = new ConcurrentList();
      lazy_list = new ConcurrentList();
      coarse_list = new ConcurrentList();

  }

	private static void run_threads(){
		for(int i = 0; i < num_threads; i++){
			thread_pool[i] = new ThreadDemo(i);
		}

		for(int i = 0; i < num_threads; i++){
			thread_pool[i].start(coarse_list, list_type, op_type);
		}
	}



	public static void main(String[] args){
    //determine what operation is to be done by reading the cmd line
    if (args.length > 0) {
      try {
          list_type = Integer.parseInt(args[0]);
          op_type = Integer.parseInt(args[1]);
          num_threads = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
          System.err.println("Argument" + args[1] + " must be an integer.");
          System.exit(1);
      }
    }

    if(num_threads > max_no_threads){
      System.out.println("ERROR: Please enter " + max_no_threads + " or less threads instead of " + num_threads);
      return;
    }

    init_lists();
		run_threads();
	}
}

class Node{
  Lock lock;
	int key;
  boolean marked;
	Node next;

  Node(int key){
    lock = new ReentrantLock();
    marked = false;
    this.key = key;
  }
}

class ThreadDemo extends Thread {
   private Thread t;
   private int thread_id;
   private static ConcurrentList list = new ConcurrentList();
   private static int list_type;
   private static int op_type;
   private static int num_factor = 150;

   //CREATE THREAD
   ThreadDemo(int id){
       thread_id = id;
       t = new Thread(this, "" + thread_id);
    }

  //RUN THREAD WITH THREAD_ID
   public void run(){ 
      try {
            switch(this.list_type){
              case 0: //coarse list
                switch(this.op_type){
                  case 0://heavy add/lower remove
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      if(!list.coarse_add(i)){
                        System.out.println("ERROR COARSE ADDING " + i + " BY THREAD: " + thread_id);
                      }
                      if(i % 2 == 0 && i != 0){//remove all even numbers after 0
                        if(!list.coarse_remove(i)){
                          System.out.println("ERROR COARSE REMOVING " + i + " BY THREAD: " + thread_id);
                        }
                      }
                    }
                    break;

                  case 1://heavy add/lower contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      if(!list.coarse_add(i)){
                        System.out.println("ERROR COARSE ADDING " + i + " BY THREAD: " + thread_id);
                      }
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        if(!list.coarse_contains(i-1)){//if no message returns then list contained i
                          System.out.println("LIST DOES NOT CONTAIN " + i + " BY THREAD: " + thread_id);
;                       }
                      }
                    }
                    break;

                  case 2://even add/contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {

                      if(!list.coarse_add(i)){
                        System.out.println("ERROR COARSE ADDING " + i + " BY THREAD: " + thread_id);
                      }
                      if(i != 0){
                        if(!list.coarse_contains(i-1)){
                          System.out.println("LIST DOES NOT CONTAIN " + i + " BY THREAD: " + thread_id);
                        }
                      }
                    }
                    break;
                    case 3://heavy contains, lower add
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.lazy_contains(i);
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        list.lazy_add(i-1);
                      }
                    }
                    break;

                  default: break;
                }
                break;
              
              case 1://fine list
                switch(this.op_type){
                  case 0://heavy add/lower remove
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {

                    if(!list.fine_add(i)){
                      System.out.println("ERROR FINE ADDING" + i + "BY THREAD: " + thread_id);
                    }
                    if(i % 2 == 0 && i != 0){//remove all even numbers after 0
                      list.fine_remove(i);
                    }
                  }
                  break;

                  case 1://heavy add/lower contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.fine_add(i);
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        list.fine_contains(i-1);
                      }
                    }
                    break;

                  case 2://even add/contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.fine_add(i);
                      if(i != 0){
                        list.fine_contains(i-1);
                      }
                    }
                    break;

                  case 3://heavy contains, lower add
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.lazy_contains(i);
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        list.lazy_add(i-1);
                      }
                    }
                    break;
                    default: break;
                }
                break;
              
              case 2://lazy list
                switch(this.op_type){
                  case 0://heavy add/lower remove
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      if(!list.lazy_add(i)){
                        System.out.println("ERROR LAZY ADDING" + i + "BY THREAD: " + thread_id);
                      }
                      if(i % 2 == 0 && i != 0){//remove all even numbers after 0
                        if(!list.lazy_remove(i)){
                          System.out.println(i + " IS NOT IN THE LIST!");
                        }
                      }
                    }
                    break;

                  case 1://heavy add/lower contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.lazy_add(i);
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        list.lazy_contains(i-1);
                      }
                    }
                    break;

                  case 2://even add/contains
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.lazy_add(i);
                      if(i != 0){
                        list.lazy_contains(i-1);
                      }
                    }
                    break;

                    case 3://heavy contains, lower add
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      list.lazy_contains(i);
                      if(i % 2 == 0  && i != 0){//contain previous odd number after 0
                        list.lazy_add(i-1);
                      }
                    }
                    break;
                  
                  default: break;
                }
                break;
              
              case 3://laziest list
             //   switch(this.op_type){
             //     case 0://heavy add/lower remove
                    for(int i = num_factor*thread_id; i < num_factor*(thread_id + 1); i++) {
                      if(!list.lazy_add(i)){
                        System.out.println("ERROR LAZIEST ADDING" + i + "BY THREAD: " + thread_id);
                      }
                      if(i % 2 == 0 && i != 0){//remove all even numbers after 0
                        if(!list.laziest_remove(i)){
                          System.out.println(i + " IS NOT IN THE LIST!");
                        }
                      }
                      System.out.println("on op " + i);
                      if(i % 5 == 0 && i !=0){//attempt to clean after every 5 operations
                        System.out.println("cleaning");
                        if(!list.clean_up()){
                          System.out.println("NO MARKED NODES");
                        }
                      }else{
                      System.out.println("didnt clean");}
                    }
                 // break;
              default: break;
           //   }
           //  break;
          }

     }catch(Exception e) {
         System.out.println("Thread " +  thread_id + " interrupted.");
     }
     //THREAD EXIT
   }
   
   public void start (ConcurrentList list, int lst_type, int o_type){
      this.list = list;
      this.list_type = lst_type;
      this.op_type = o_type;
      //START THREAD
      t.run();
   }

}