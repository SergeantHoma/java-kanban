package tasks;
import abstractClass.Task;

public class SingleTask extends Task {

        protected SingleTask(String name, String description) {
            super(name, description);
        }

        private SingleTask(String name, String description, int id, Status status) {
            super(name, description,id);
            this.status = status;
        }

        @Override
        public TypeOfTask getType() {
            return TypeOfTask.SINGLE_TASK;
        }


        protected SingleTask update(String name,String description, Status status){
            return new SingleTask(
                    name,
                    description,
                    this.getIdTask(),
                    status
            );
        }

        @Override
        public String toString() {
            return "Tasks.SingleTask{" +
                    "name='" + this.getName() + '\'' +
                    ", description='" + this.getDescription() + '\'' +
                    ", idTask=" + this.getIdTask() +
                    ", status=" + this.getStatus() +
                    '}';
        }
}
